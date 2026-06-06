package com.example.likelionkang;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class AstTestRunner {

    // 3대 레이어 및 기타 객체를 저장할 글로벌 리스트
    static List<String> controllers = new ArrayList<>();
    static List<String> services = new ArrayList<>();
    static List<String> repositories = new ArrayList<>();
    static List<String> others = new ArrayList<>();

    // React Flow 표준 데이터 모델 클래스 정의 (JSON 변환용)
    static class ReactFlowNode {
        public String id;
        public String type = "default";
        public Map<String, String> data = new HashMap<>();
        public Map<String, Integer> position = new HashMap<>();

        public ReactFlowNode(String id, String label, int x, int y) {
            this.id = id;
            this.data.put("label", label);
            this.position.put("x", x);
            this.position.put("y", y);
        }
    }

    static class ReactFlowEdge {
        public String id;
        public String source;
        public String target;
        public boolean animated = true;

        public ReactFlowEdge(String source, String target) {
            this.id = "e-" + source + "-" + target;
            this.source = source;
            this.target = target;
        }
    }

    static class ReactFlowData {
        public List<ReactFlowNode> nodes = new ArrayList<>();
        public List<ReactFlowEdge> edges = new ArrayList<>();
    }

    public static void main(String[] args) {
        // 1. 소스 코드가 모여있는 패키지 루트 폴더 경로
        String srcPath = "src/main/java/com/example/likelionkang";
        File projectDir = new File(srcPath);

        System.out.println("==================================================");
        System.out.println(" 🔍 프로젝트 패키지 내 자바 파일 AST 분석 시작... ");
        System.out.println("==================================================");

        // 2. 모든 폴더를 돌며 자바 파일 파싱 및 3대 레이어 분류
        analyzeDirectory(projectDir);

        // 3. 콘솔창에 텍스트 트리 구조 시각화 출력
        printProjectTree();

        // 4. React Flow용 데이터 생성 및 가로 배치 알고리즘 계산
        generateReactFlowJson();
    }

    /**
     * 폴더를 탐색하며 자바 파일을 찾아 JavaParser로 AST 분석을 수행하는 메서드
     */
    private static void analyzeDirectory(File node) {
        if (node.isDirectory()) {
            File[] subNodes = node.listFiles();
            if (subNodes != null) {
                for (File subNode : subNodes) {
                    analyzeDirectory(subNode);
                }
            }
        } else if (node.isFile() && node.getName().endsWith(".java")) {
            try (FileInputStream in = new FileInputStream(node)) {
                // JavaParser를 통해 파일 하나를 구문 트리(AST) 객체로 변환
                CompilationUnit cu = StaticJavaParser.parse(in);

                // 파일 내부의 클래스 또는 인터페이스 선언부 노드들을 모두 탐색
                cu.findAll(ClassOrInterfaceDeclaration.class).forEach(clazz -> {
                    String className = clazz.getNameAsString();
                    boolean classified = false;

                    // 1. Controller 분류 (어노테이션 혹은 접미사 검사)
                    if (clazz.getAnnotationByName("RestController").isPresent() ||
                            clazz.getAnnotationByName("Controller").isPresent() ||
                            className.endsWith("Controller")) {
                        controllers.add(className);
                        classified = true;
                    }

                    // 2. Service 분류 (어노테이션 혹은 접미사 검사)
                    if (clazz.getAnnotationByName("Service").isPresent() ||
                            className.endsWith("Service")) {
                        services.add(className);
                        classified = true;
                    }

                    // 3. Repository 분류 (어노테이션, 인터페이스 상속, 혹은 접미사 검사로 완벽 보정)
                    if (clazz.getAnnotationByName("Repository").isPresent() ||
                            className.endsWith("Repository") ||
                            clazz.getImplementedTypes().stream().anyMatch(t -> t.getNameAsString().equals("JpaRepository"))) {
                        repositories.add(className);
                        classified = true;
                    }

                    // 4. 스프링 핵심 3대 레이어가 아닌 일반 클래스/DTO/Entity 등
                    if (!classified) {
                        // 현재 테스트 러너 파일 자체는 제외하고 수집
                        if (!className.equals("AstTestRunner")) {
                            others.add(className);
                        }
                    }
                });
            } catch (Exception e) {
                System.err.println(node.getName() + " 파일 파싱 실패: " + e.getMessage());
            }
        }
    }

    /**
     * 분석 완료된 컴포넌트들을 트리 구조 형태로 콘솔에 찍어주는 메서드
     */
    private static void printProjectTree() {
        System.out.println("\n[Root] LikeLion Application (AST Abstracted Tree)");
        System.out.println(" ┃");

        // 1. Controller Layer
        System.out.println(" ┣━━ 📂 Web / Controller Layer (요청 접수)");
        if (controllers.isEmpty()) System.out.println(" ┃    ┗━━ ⚠️ 발견된 컨트롤러가 없습니다.");
        for (int i = 0; i < controllers.size(); i++) {
            String prefix = (i == controllers.size() - 1) ? " ┃    ┗━━ 📄 " : " ┃    ┣━━ 📄 ";
            System.out.println(prefix + controllers.get(i));
        }
        System.out.println(" ┃");

        // 2. Service Layer
        System.out.println(" ┣━━ 📂 Business / Service Layer (비즈니스 로직)");
        if (services.isEmpty()) System.out.println(" ┃    ┗━━ ⚠️ 발견된 서비스가 없습니다.");
        for (int i = 0; i < services.size(); i++) {
            String prefix = (i == services.size() - 1) ? " ┃    ┗━━ 📄 " : " ┃    ┣━━ 📄 ";
            System.out.println(prefix + services.get(i));
        }
        System.out.println(" ┃");

        // 3. Repository Layer
        System.out.println(" ┣━━ 📂 Data Access / Repository Layer (DB 연결)");
        if (repositories.isEmpty()) System.out.println(" ┃    ┗━━ ⚠️ 발견된 리포지토리가 없습니다.");
        for (int i = 0; i < repositories.size(); i++) {
            String prefix = (i == repositories.size() - 1) ? " ┃    ┗━━ 📄 " : " ┃    ┣━━ 📄 ";
            System.out.println(prefix + repositories.get(i));
        }
        System.out.println(" ┃");



    }

    /**
     * React Flow 형태의 구조에 맞춰 시각화용 JSON 파일을 내보내는 메서드
     */
    private static void generateReactFlowJson() {
        ReactFlowData flowData = new ReactFlowData();
        int stepY = 120; // 노드 간 위아래 간격

        // Controller 노드 배치 (X좌표: 100)
        for (int i = 0; i < controllers.size(); i++) {
            flowData.nodes.add(new ReactFlowNode(controllers.get(i), controllers.get(i), 100, 100 + (i * stepY)));
        }

        // Service 노드 배치 (X좌표: 450)
        for (int i = 0; i < services.size(); i++) {
            flowData.nodes.add(new ReactFlowNode(services.get(i), services.get(i), 450, 100 + (i * stepY)));
        }

        // Repository 노드 배치 (X좌표: 800)
        for (int i = 0; i < repositories.size(); i++) {
            flowData.nodes.add(new ReactFlowNode(repositories.get(i), repositories.get(i), 800, 100 + (i * stepY)));
        }

        // 이름 매칭 기반 의존성선(Edge) 연결 작업 (예: PostController -> PostService -> PostRepository)
        for (String controller : controllers) {
            String prefix = controller.replace("Controller", "");
            for (String service : services) {
                if (service.startsWith(prefix)) {
                    flowData.edges.add(new ReactFlowEdge(controller, service));
                    for (String repo : repositories) {
                        if (repo.startsWith(prefix) || repo.replace("Repository", "").startsWith(prefix.substring(0, Math.min(3, prefix.length())))) {
                            flowData.edges.add(new ReactFlowEdge(service, repo));
                        }
                    }
                }
            }
        }

        // JSON 파일 저장 실행
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            // 프로젝트 최상위 루트에 'react-flow-data.json'으로 저장됩니다.
            mapper.writeValue(new File("react-flow-data.json"), flowData);


        } catch (Exception e) {
            System.err.println("JSON 변환 중 실패: " + e.getMessage());
        }
    }
}