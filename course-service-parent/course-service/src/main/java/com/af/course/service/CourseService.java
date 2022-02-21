package com.af.course.service;

import com.af.common.base.BaseService;
import com.af.common.utils.JwtUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.entity.Course;
import com.af.course.api.entity.Trainee;
import com.af.course.api.vo.CourseQueryDto;
import com.af.course.api.vo.CourseSelectedVo;
import com.af.course.api.vo.CourseVo;
import com.af.course.mapper.CourseMapper;
import com.af.course.mapper.TraineeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:14
 */
@Service
@AllArgsConstructor
public class CourseService extends BaseService<CourseMapper, Course> {

    private final TraineeMapper traineeMapper;

    public PageVO<CourseVo> searchCourse(PageInfo<CourseVo> pageInfo, CourseQueryDto courseQueryDto) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<CourseVo> page = new PageInfo<>(this.mapper.searchCourseByKeyword(courseQueryDto));

        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), page.getList());
    }

    /**
     * 学生端查询课程
     * @param pageInfo
     * @param courseQueryDto
     * @param token
     * @return PageVO<CourseSelectedVo>
     */
    public PageVO<CourseSelectedVo> searchCourseByStudent(PageInfo<CourseSelectedVo> pageInfo,
                                                          CourseQueryDto courseQueryDto,
                                                          String token) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo<CourseVo> page = new PageInfo<>(this.mapper.searchCourseByKeyword(courseQueryDto));

        List<CourseVo> courseVoList = page.getList();

        // 查询该学生的课程
        Long userId = JwtUtil.getUserId(token);
        Trainee trainee = new Trainee();
        trainee.setUserId(userId.toString());
        List<String> userCourseIdList = traineeMapper.findList(trainee).stream().
                map(Trainee::getCourseId).collect(Collectors.toList());

        List<CourseSelectedVo> res = new ArrayList<>();

        courseVoList.forEach(courseVo -> {
            if (userCourseIdList.
                    contains(courseVo.getCourseId())) {
                res.add(new CourseSelectedVo(courseVo, true));
            } else {
                res.add(new CourseSelectedVo(courseVo, false));
            }
        });
        for (CourseSelectedVo r : res) {
            System.out.println(r.isSelected());
        }
        return new PageVO<>(page.getTotal(), page.getPages(), page.getPageNum(), page.getPageSize(), res);
    }


}
