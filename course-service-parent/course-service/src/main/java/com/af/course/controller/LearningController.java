package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.exceptions.BusinessException;
import com.af.common.model.ResponseBean;
import com.af.common.utils.DateUtil;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.constant.CourseResultCode;
import com.af.course.api.entity.Learning;
import com.af.course.api.vo.LearningAddRequest;
import com.af.course.api.vo.LearningStatusVo;
import com.af.course.api.vo.LessonLearningVo;
import com.af.course.service.LearningService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Tanglinfeng
 * @date 2022/2/26 16:43
 */
@Api(tags = "学习信息接口")
@AllArgsConstructor
@RestController
@RequestMapping("/v1/learning")
public class LearningController {

    private final LearningService learningService;

    @ApiOperation("添加学习信息")
    @PostMapping("/add")
    public ResponseBean<Boolean> addLearning(@RequestBody LearningAddRequest learningAddRequest) {
        if (learningService.existLearningTitle(learningAddRequest.getLearningTitle())) {
            throw new BusinessException(CourseResultCode.LEARNING_TITLE_EXIST);
        }
        return new ResponseBean<>(learningService.batchInsertLearning(learningAddRequest));
    }


    @ApiOperation("作业列表")
    @GetMapping("/list")
    public ResponseBean<PageVO<LessonLearningVo>> findLearningList(@RequestParam("courseId") String courseId,
                                                                   @RequestParam("pageNum") Integer pageNum,
                                                                   @RequestParam("pageSize") Integer pageSize) {
        PageInfo<LessonLearningVo> pageInfo = PageUtil.pageInfo(pageNum, pageSize);
        Learning learning = new Learning();
        learning.setCourseId(courseId);
        return new ResponseBean<>(learningService.findLearningList(pageInfo, learning));
    }

    @ApiOperation("学生作业列表")
    @GetMapping("/listByStudent")
    public ResponseBean<PageVO<LearningStatusVo>> findLearningList(@RequestParam("courseId") String courseId,
                                                                   @RequestParam("pageNum") Integer pageNum,
                                                                   @RequestParam("pageSize") Integer pageSize,
                                                                   @RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        PageInfo<LearningStatusVo> pageInfo = PageUtil.pageInfo(pageNum, pageSize);
        Learning learning = new Learning();
        learning.setCourseId(courseId);
        return new ResponseBean<>(learningService.learningStatusList(pageInfo, learning, token));
    }

    @ApiOperation("查询单次作业")
    @PostMapping("/get")
    public ResponseBean<Learning> get(@RequestBody Learning learning) {
        return new ResponseBean<>(learningService.get(learning));
    }

    @ApiOperation("更新作业截止日期")
    @PutMapping("/updateLimitDate")
    public ResponseBean<Boolean> update(@RequestParam("limitDate") String limitDate, @RequestParam("learningTitle") String learningTitle) {
        Learning learning = new Learning();
        learning.setLimitDate(DateUtil.strToDate(limitDate));
        learning.setLearningTitle(learningTitle);
        learning.setModifyDate(new Date());
        return new ResponseBean<>(learningService.update(learning));
    }
}
