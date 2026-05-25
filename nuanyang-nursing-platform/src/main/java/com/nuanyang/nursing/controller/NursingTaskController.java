package com.nuanyang.nursing.controller;

import com.nuanyang.common.core.domain.R;
import com.nuanyang.nursing.dto.NursingTaskDto;
import com.nuanyang.nursing.dto.TaskDto;
import com.nuanyang.nursing.vo.NursingTaskVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nuanyang.common.annotation.Log;
import com.nuanyang.common.core.controller.BaseController;
import com.nuanyang.common.core.domain.AjaxResult;
import com.nuanyang.common.enums.BusinessType;
import com.nuanyang.nursing.domain.NursingTask;
import com.nuanyang.nursing.service.INursingTaskService;
import com.nuanyang.common.core.page.TableDataInfo;

/**
 * 护理任务Controller
 *
 * @author byte-love
 * @date 2024-09-27
 */
@RestController
@RequestMapping("/nursing/nursingTask")
public class NursingTaskController extends BaseController
{
    @Autowired
    private INursingTaskService nursingTaskService;

    /**
     * 查询护理任务列表
     */
    @GetMapping("/list")
    public TableDataInfo list(NursingTaskDto nursingTaskDto)
    {
        TableDataInfo tableDataInfo = nursingTaskService.selectNursingTaskList(nursingTaskDto);
        return tableDataInfo;
    }

    /**
     * 获取护理任务详细信息
     */
    @GetMapping(value = "/{id}")
    public R<NursingTaskVo> getInfo(@PathVariable("id") Long id)
    {
        NursingTaskVo nursingTaskVo = nursingTaskService.selectNursingTaskById(id);

        return R.ok(nursingTaskVo);
    }

    @PutMapping("/cancel")
    public AjaxResult cancelTask(@RequestBody TaskDto taskDto) {
        return toAjax(nursingTaskService.cancelTask(taskDto));
    }

    @PutMapping("/updateTime")
    public AjaxResult rescheduleTask(@RequestBody TaskDto taskDto) {
        return toAjax(nursingTaskService.rescheduleTask(taskDto));
    }

    @PutMapping("/do")
    public AjaxResult executeTask(@RequestBody TaskDto taskDto) {
        return toAjax(nursingTaskService.executeTask(taskDto));
    }

    /**
     * 新增护理任务
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingTask:add')")
    @Log(title = "护理任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NursingTask nursingTask)
    {
        return toAjax(nursingTaskService.insertNursingTask(nursingTask));
    }

    /**
     * 修改护理任务
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingTask:edit')")
    @Log(title = "护理任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NursingTask nursingTask)
    {
        return toAjax(nursingTaskService.updateNursingTask(nursingTask));
    }

    /**
     * 删除护理任务
     */
    @PreAuthorize("@ss.hasPermi('nursing:nursingTask:remove')")
    @Log(title = "护理任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(nursingTaskService.deleteNursingTaskByIds(ids));
    }
}
