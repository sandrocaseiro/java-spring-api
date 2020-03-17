package com.sandrocaseiro.apitemplate.mappers;

import com.sandrocaseiro.apitemplate.models.DPage;
import com.sandrocaseiro.apitemplate.models.domain.ERole;
import com.sandrocaseiro.apitemplate.models.domain.EUser;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserCreateReq;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserCreateResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserGroupResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserReportResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserResp;
import com.sandrocaseiro.apitemplate.models.dto.user.DUserUpdateReq;
import com.sandrocaseiro.apitemplate.models.jpa.JUserGroup;
import com.sandrocaseiro.apitemplate.models.service.SUser;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(imports = {ERole.class, Collectors.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = PageMapper.class)
public interface UserMapper {
    @Mapping(target = "roles", expression = "java( model.getRoles().stream().map(ERole::new).collect(Collectors.toList()) )")
    EUser toEUser(DUserCreateReq model);

    @Mapping(target = "roles", expression = "java( model.getRoles().stream().map(ERole::getId).collect(Collectors.toList()) )")
    DUserCreateResp toDUserCreateResp(EUser model);

    @Mapping(target = "roles", expression = "java( model.getRoles().stream().map(ERole::new).collect(Collectors.toList()) )")
    EUser toEUser(DUserUpdateReq model);

    DUserGroupResp toDUserGroupResp(JUserGroup user);

    @Mapping(target = "group", source = "user.group.name")
    DUserGroupResp toDUserGroupResp(EUser user);

    List<DUserGroupResp> toDUserGroupRespList(List<EUser> users);

    DUserResp toDUserResp(EUser user);

    List<DUserResp> toDUserRespList(List<EUser> users);

    DUserResp toDUserResp(SUser user);

    @Mapping(target = "rolesCount", expression = "java( user.getRoles().size() )")
    @Mapping(target = "balance", expression = "java( currency + \" \" + user.getBalance() )")
    DUserReportResp toDUserReportResp(EUser user, String currency);

    default List<DUserReportResp> toDUserReportRespList(List<EUser> users, String currency) {
        return users.stream().map(u -> toDUserReportResp(u, currency)).collect(Collectors.toList());
    }

    default DPage<DUserGroupResp> toDUserGroupRespPaged(Page<EUser> users) {
        return PageMapper.toDPage(users.map(this::toDUserGroupResp));
    }
}
