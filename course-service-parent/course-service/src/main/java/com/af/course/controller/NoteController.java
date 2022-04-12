package com.af.course.controller;

import com.af.common.constant.TokenConstants;
import com.af.common.model.ResponseBean;
import com.af.common.utils.JwtUtil;
import com.af.common.utils.PageUtil;
import com.af.common.vo.PageVO;
import com.af.course.api.entity.Note;
import com.af.course.service.NoteService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tanglinfeng
 * @date 2022/4/11 23:51
 */
@Api(tags = "笔记本信息接口")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/note")
public class NoteController {

    private final NoteService noteService;

    @ApiOperation("添加/更新笔记")
    @PostMapping("/save")
    public ResponseBean<Boolean> createNote(@RequestHeader(TokenConstants.TOKEN_HEADER) String token, @RequestBody Note note) {
        Long userId = JwtUtil.getUserId(token);
        note.setUserId(userId.toString());

        return new ResponseBean<>(noteService.save(note));
    }

    @ApiOperation("根据ID删除笔记")
    @DeleteMapping("/delete")
    public ResponseBean<Boolean> deleteNoteById(@RequestParam("id") Long id) {
        return new ResponseBean<>(noteService.deleteById(id));
    }

    @ApiOperation("分页查询笔记")
    @GetMapping("/findList")
    public ResponseBean<PageVO<Note>> findPage(@RequestHeader(TokenConstants.TOKEN_HEADER) String token,
                                               @RequestParam("pageNum") Integer pageNum,
                                               @RequestParam("pageSize") Integer pageSize,
                                               @RequestParam("noteTitle") String noteTitle) {
        PageInfo<Note> pageInfo = PageUtil.pageInfo(pageNum, pageSize, "modify_date", "DESC");
        Note note = new Note();
        note.setNoteTitle(noteTitle);
        note.setUserId(JwtUtil.getUserId(token).toString());
        return new ResponseBean<>(noteService.findPage(pageInfo, note));
    }

    @ApiOperation("根据ID查询笔记")
    @GetMapping("/findById")
    public ResponseBean<Note> getById(@RequestParam("id") Long id) {
        return new ResponseBean<>(noteService.getById(id));
    }
}
