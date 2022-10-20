package cn.microboat.mapper;

import cn.microboat.core.BasicMapper;
import cn.microboat.mapper.converter.UserMapperConverter;
import cn.microboat.core.pojo.dto.UserDto;
import cn.microboat.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * User 转 UserDto
 *
 * @author zhouwei
 */
@Mapper(
        componentModel = "spring",
        uses = UserMapperConverter.class,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface User2DtoMapper extends BasicMapper<User, UserDto> {
}
