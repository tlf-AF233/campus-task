package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.entity.CheckInRecord;
import com.af.course.api.vo.CheckInQueryRequest;
import com.af.course.api.vo.CheckInRecordVo;
import com.af.course.api.vo.CheckInRequest;
import com.af.course.service.CheckInRecordService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tanglinfeng
 * @date 2022/4/22 13:07
 */
@Api(tags = "签到信息记录接口")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/checkIn")
public class CheckInRecordController {

    private final CheckInRecordService checkInRecordService;


    /**
     * 查询学生签到记录列表分页接口
     *
     * @param request
     * @return
     */
    @ApiOperation("查询学生签到记录列表分页接口")
    @PostMapping("/list")
    public ResponseBean<PageVO<CheckInRecordVo>> findList(@RequestBody CheckInQueryRequest request) {
        PageInfo<CheckInRecordVo> pageInfo = PageUtil.pageInfo(request.getPageNum(), request.getPageSize());
        return new ResponseBean<>(checkInRecordService.findVoList(pageInfo, request));
    }

    /**
     * 拉取所有签到名称
     *
     * @param courseId
     * @return
     */
    @ApiOperation("签到记录名称列表")
    @GetMapping("/listByName")
    public ResponseBean<List<String>> listByCheckInName(@RequestParam("courseId") String courseId) {
        return new ResponseBean<>(checkInRecordService.findCheckInNameList(courseId));
    }

    @ApiOperation("最新一次签到是否完成")
    @GetMapping("/isChecked")
    public ResponseBean<Boolean> isChecked(@RequestParam("courseId") String courseId, @RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        return new ResponseBean<>(checkInRecordService.isChecked(courseId, token));
    }

    @ApiOperation("签到")
    @PostMapping("/check")
    public ResponseBean<Boolean> checkIn(@RequestBody CheckInRequest checkInRequest,
                                         @RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        return new ResponseBean<>(checkInRecordService.check(checkInRequest, token));
    }

    @ApiOperation("老师代签")
    @PostMapping("/checkByTeacher")
    public ResponseBean<Boolean> checkByTeacher(@RequestBody CheckInRecord checkInRecord) {
        return new ResponseBean<>(checkInRecordService.checkByTeacher(checkInRecord));
    }

    @ApiOperation("发起签到")
    @PostMapping("/init")
    public ResponseBean<Void> initCheck(@RequestBody CheckInRequest checkInRequest) {
        checkInRecordService.initCheckCode(checkInRequest);
        return new ResponseBean<>();
    }
}
