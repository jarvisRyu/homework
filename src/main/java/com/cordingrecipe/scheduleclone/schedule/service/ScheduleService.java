package com.cordingrecipe.scheduleclone.schedule.service;

import com.cordingrecipe.scheduleclone.schedule.dto.request.ScheduleSaveRequestDto;
import com.cordingrecipe.scheduleclone.schedule.dto.request.ScheduleUPdateRequestDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.ScheduleSaveResponseDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.ScheduleResponseDto;
import com.cordingrecipe.scheduleclone.schedule.dto.response.ScheduleUpdateResponseDto;
import com.cordingrecipe.scheduleclone.schedule.entity.Schedule;
import com.cordingrecipe.scheduleclone.user.entity.User;
import com.cordingrecipe.scheduleclone.schedule.repository.ScheduleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service //Service bean등록  @Component
@RequiredArgsConstructor //final 필드 생성자 자동생성
public class ScheduleService {
    //의존 객체 생성
    private final ScheduleRepository scheduleRepository;

    @Transactional //작업이 끝나면 자동으로 commit 예외발생시 롤백
    //일정저장 파라미터(userId,dto: title,content)
    public ScheduleSaveResponseDto save(Long userId, ScheduleSaveRequestDto dto) {
        //user id 매핑
        User user = User.fromUserId(userId);
        //일정 entity 매핑
        Schedule schedule = new Schedule(user, dto.getTitle(), dto.getContent());
        //DB에 저장
        scheduleRepository.save(schedule);
        //반환 : dto에 맵핑
        return new ScheduleSaveResponseDto(
                schedule.getId(),
                user.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true) //조회만 하는경우
    // 성능향상 된다고함.실수로 데이터바꾸는것을 방지
    public List<ScheduleResponseDto> findAll() {
        //scheduleRepository.findAll()=리스트타입
        //schedule 데이터를 담은 Dto 생성
        return scheduleRepository.findAll().stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getUser().getId(),
                        schedule.getTitle(),
                        schedule.getContents(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()))
                .collect(Collectors.toList());//stream 처리한 결과를 List로 모으는 동작
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findeOne(Long id) {
        //findById = Optional 타입 -null일 수도 있는 값을 감싸는 래퍼객체
        Schedule schedule = scheduleRepository.findById(id)  // orElseThrow 값이있으면 꺼내고 없으면 예외발생
                //IllegalArgumentException - 메서드 파라미터가 유효하지 않을때 발생
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    @Transactional //하나의 작업단위로 엮음.
    public ScheduleUpdateResponseDto update(Long scheduleId, Long userId, @Valid ScheduleUPdateRequestDto dto) {
        Schedule schedule = scheduleRepository.findById(scheduleId) //id로 등록되있는 일정 찾기
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        if (!userId.equals(schedule.getUser().getId())) {
            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        }
        schedule.update(dto.getTitle(), dto.getContent()); //
        return new ScheduleUpdateResponseDto(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    @Transactional
    public void deleteById(Long scheduleId, Long userId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 존재하지 않습니다."));
        if (!userId.equals(schedule.getUser().getId())) { //userId 값과
            throw new IllegalArgumentException("본인이 작성한 스케줄만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }


}
