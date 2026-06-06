package com.example.likelionbe.domain.contract.mapper;

import com.example.likelionbe.domain.contract.dto.ChecklistResDto;
import com.example.likelionbe.domain.contract.entity.Checklist;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Checklist 엔티티와 DTO 간 변환을 담당하는 매퍼 클래스입니다.
 */
@Component
public class ChecklistMapper {

    /**
     * 계약 ID와 Checklist 목록을 ChecklistResDto로 변환합니다.
     *
     * @param contractId 계약 ID
     * @param checklists 체크리스트 엔티티 목록
     * @return 변환된 ChecklistResDto
     */
    public ChecklistResDto toChecklistResDto(Long contractId, List<Checklist> checklists) {
        List<ChecklistResDto.ChecklistItemResDto> items = checklists.stream()
                .map(this::toChecklistItemResDto)
                .toList();

        return ChecklistResDto.builder()
                .contractId(contractId)
                .items(items)
                .build();
    }

    /**
     * Checklist 엔티티를 ChecklistItemResDto로 변환합니다.
     *
     * @param checklist 변환할 Checklist 엔티티
     * @return 변환된 ChecklistItemResDto
     */
    public ChecklistResDto.ChecklistItemResDto toChecklistItemResDto(Checklist checklist) {
        return ChecklistResDto.ChecklistItemResDto.builder()
                .checklistId(checklist.getId())
                .checklistCode(checklist.getChecklistCode())
                .title(checklist.getTitle())
                .description(checklist.getDescription())
                .sortOrder(checklist.getSortOrder())
                .required(checklist.getRequired())
                .checked(checklist.getChecked())
                .checkedAt(checklist.getCheckedAt())
                .build();
    }
}
