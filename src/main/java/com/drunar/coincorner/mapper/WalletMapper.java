package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.dto.WalletReadDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface WalletMapper {

    WalletMapper INSTANCE = Mappers.getMapper( WalletMapper.class );

    @Mapping(source = "ownerUser.id", target = "ownerId")
    WalletReadDTO walletToWalletReadDTO(Wallet wallet);

    @Mapping(source = "ownerId", target = "ownerUser.id")
    Wallet WalletReadDTOToWallet(WalletReadDTO walletDTO);



}
