package com.nuanyang.nursing.controller.member;

import java.util.List;

import com.nuanyang.common.core.domain.R;
import com.nuanyang.common.utils.UserThreadLocal;
import com.nuanyang.nursing.dto.MemberElderDto;
import com.nuanyang.nursing.dto.UserLoginRequestDto;
import com.nuanyang.nursing.service.IFamilyMemberElderService;
import com.nuanyang.nursing.vo.FamilyMemberElderVo;
import com.nuanyang.nursing.vo.LoginVo;
import com.nuanyang.nursing.vo.MemberElderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nuanyang.common.core.controller.BaseController;
import com.nuanyang.common.core.domain.AjaxResult;
import com.nuanyang.nursing.service.IFamilyMemberService;

/**
 * 老人家属Controller
 * 
 * @author byte-love
 * @date 2025-08-26
 */
@RestController
@RequestMapping("/member/user")
public class FamilyMemberController extends BaseController
{
    @Autowired
    private IFamilyMemberService familyMemberService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        LoginVo loginVo = familyMemberService.login(userLoginRequestDto);
        return success(loginVo);

    }

    @Autowired
    private IFamilyMemberElderService familyMemberElderService;

    /**
     * 新增客户老人关联记录
     *
     * @param memberElderDto 客户老人关联 DTO
     * @return 操作结果
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增客户老人关联记录")
    public AjaxResult add(@RequestBody MemberElderDto memberElderDto) {
        return toAjax(familyMemberElderService.add(memberElderDto));
    }

    /**
     * 我的家人列表
     *
     * @return 客户老人关联实体类
     */
    @GetMapping("/my")
    @ApiOperation(value = "我的家人列表")
    public R<List<FamilyMemberElderVo>> my() {

        //获取当前线程中的用户信息
        Long userId = UserThreadLocal.getUserId();
        System.out.println(userId);

        List<FamilyMemberElderVo> memberElders = familyMemberElderService.my();
        return R.ok(memberElders);
    }

    @GetMapping("/list-by-page")
    @ApiOperation(value = "分页查询客户老人关联记录")
    public R<List<MemberElderVo>> listByPage() {
        startPage();
        Long userId = UserThreadLocal.getUserId();
        List<MemberElderVo> memberElders = familyMemberElderService.listByPage(userId);
        return R.ok(memberElders);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "根据id删除客户老人关联记录")
    public AjaxResult deleteById(@RequestParam Long id) {
        return toAjax(familyMemberElderService.deleteById(id));
    }



}
