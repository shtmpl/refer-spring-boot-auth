package refer.spring.boot.auth.controller.api;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import refer.spring.boot.auth.controller.api.request.RequestAccountSave;
import refer.spring.boot.auth.controller.api.response.ResponseAccount;
import refer.spring.boot.auth.domain.Account;

@Mapper
public interface ApiMapper {

    ApiMapper INSTANCE = Mappers.getMapper(ApiMapper.class);


    Account toAccount(RequestAccountSave request);

    ResponseAccount toResponseAccount(Account account);
}
