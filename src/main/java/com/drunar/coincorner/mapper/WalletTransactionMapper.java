package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.WalletTransaction;
import com.drunar.coincorner.dto.WalletTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface WalletTransactionMapper {

    WalletTransactionMapper INSTANCE = Mappers.getMapper( WalletTransactionMapper.class );

    @Mapping(source = "walletId", target = "wallet.id")
    @Mapping(source = "walletName", target = "wallet.walletName")
    WalletTransaction walletTransactionDTOToWallet(WalletTransactionDTO walletDTO);

    @Mapping(source = "wallet.id", target = "walletId")
    @Mapping(source = "wallet.walletName", target = "walletName")
    WalletTransactionDTO walletTransactionToWalletTransactionDTO(WalletTransaction walletTransaction);


}
