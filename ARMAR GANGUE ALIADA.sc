SCRIPT_START
{
NOP

loopprincipal:

WAIT 0
    LVAR_FLOAT x
    LVAR_FLOAT y
    LVAR_FLOAT z
 //----------------------------   
    LVAR_INT arma_do_jogador
    LVAR_INT jogador
    LVAR_INT max_peds
    LVAR_INT pedatual
    LVAR_INT pedtype
    LVAR_INT pedsperto

IF IS_KEY_PRESSED VK_KEY_M
    
        max_peds=0
        GET_PLAYER_CHAR 0 jogador  
        GET_CHAR_COORDINATES jogador x y z
        GET_CURRENT_CHAR_WEAPON jogador arma_do_jogador
        WHILE max_peds<100
            GET_RANDOM_CHAR_IN_AREA_OFFSET_NO_SAVE x y z 100000.0 100000.0 100000.0 pedatual  
            GET_PED_TYPE pedatual pedtype
            IF pedtype = 8
                SET_CURRENT_CHAR_WEAPON pedatual arma_do_jogador
                EXPLODE_CHAR_HEAD pedatual
            ENDIF
            max_peds++
        ENDWHILE
    
        PRINT_STRING_NOW "ARMOU SEUS ALIADOS COM A SUA ARMA" 1999
ENDIF
WAIT 0
GOTO loopprincipal

}
SCRIPT_END