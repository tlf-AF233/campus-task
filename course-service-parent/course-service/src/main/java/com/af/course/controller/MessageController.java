package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.entity.Message;
import com.af.course.api.vo.StudentMessageVo;
import com.af.course.service.MessageService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Tanglinfeng
 * @date 2022/4/9 16:35
 */
@Api(tags = "消息信息接口")
@RestController
@RequestMapping("/v1/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @ApiOperation("创建消息通知")
    @PostMapping("/add")
    public ResponseBean<Boolean> createMessage(@RequestBody Message message) {
        return new ResponseBean<>(messageService.addMessage(message));
    }

    @ApiOperation("更新消息通知")
    @PutMapping("/update")
    public ResponseBean<Boolean> updateMessage(@RequestBody Message message) {
        return new ResponseBean<>(messageService.update(message));
    }

    @ApiOperation("查询消息")
    @GetMapping("/findById")
    public ResponseBean<Message> findById(@RequestParam("id") Long id) {
        return new ResponseBean<>(messageService.getById(id));
    }

    @ApiOperation("分页接口")
    @GetMapping("/findList")
    public ResponseBean<PageVO<Message>> findPage(@RequestParam("pageSize") Integer pageSize,
                                                  @RequestParam("pageNum") Integer pageNum,
                                                  @RequestParam("courseId") String courseId) {
        Message message = new Message();
        message.setCourseId(courseId);
        PageInfo<Message> pageInfo = PageUtil.pageInfo(pageNum, pageSize, "create_date", "DESC");
        return new ResponseBean<>(messageService.findPage(pageInfo, message));
    }

    @ApiOperation("查找学生所有消息")
    @GetMapping("/findListByStudent")
    public ResponseBean<List<StudentMessageVo>> findMessageVoList(@RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        return new ResponseBean<>(messageService.findMessageVoList(token));
    }

    @ApiOperation("删除消息")
    @DeleteMapping("/delete")
    public ResponseBean<Boolean> deleteById(@Param("id") Long id) {
        return new ResponseBean<>(messageService.deleteById(id));
    }

    @ApiOperation("是否有未读消息")
    @GetMapping("/unread")
    public ResponseBean<Boolean> checkMessage(@RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        return new ResponseBean<>(messageService.checkMessage(token));
    }

    @ApiOperation("读所有消息")
    @PostMapping("/read")
    public ResponseBean<Void> readMessage(@RequestHeader(TokenConstants.TOKEN_HEADER) String token) {
        messageService.readMessage(token);
        return new ResponseBean<>();
    }

}
