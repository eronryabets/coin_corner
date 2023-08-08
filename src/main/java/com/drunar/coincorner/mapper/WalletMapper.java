package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.Wallet;
import com.drunar.coincorner.dto.WalletCreateEditDTO;
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
    @Mapping(source = "ownerUser.username", target = "ownerUsername")
    WalletReadDTO walletToWalletReadDTO(Wallet wallet);

    @Mapping(source = "ownerId", target = "ownerUser.id")
    @Mapping(target = "transactions", ignore = true)
    Wallet walletReadDTOToWallet(WalletReadDTO walletDTO);

    @Mapping(source = "ownerUser.id", target = "ownerId")
    WalletCreateEditDTO walletToWalletCreateEditDTO(Wallet wallet);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "ownerId", target = "ownerUser.id")
    @Mapping(target = "transactions", ignore = true)
    Wallet walletCreateEditDTOToWallet(WalletCreateEditDTO walletDTO);

    default Wallet walletCreateEditDTOCopyToWallet(WalletCreateEditDTO object, Wallet wallet){
        wallet.setWalletName(object.getWalletName());
        wallet.setWalletType(Wallet.WalletType.valueOf(object.getWalletType()));
        wallet.setCurrency(Wallet.Currency.valueOf(object.getCurrency()));
        wallet.setBalance(object.getBalance());
        return wallet;
    }



}
