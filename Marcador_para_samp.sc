/*
    Autor: Gabriel Alves (Gabriel432135)
*/
SCRIPT_START
{
NOP

LVAR_INT char blip ativador contador rastreador
LVAR_FLOAT x y z
ativador = 0

    loop:
    IF IS_KEY_PRESSED 220
    AND IS_KEY_PRESSED 221
        IF ativador = 0
            ativador = 1
            REQUEST_MODEL 1930
            LOAD_ALL_MODELS_NOW
            PRINT_STRING_NOW "MARCADOR ATIVADO." 1000
            WAIT 500
        ELSE
            desativar:
            ativador = 0
            MARK_MODEL_AS_NO_LONGER_NEEDED 1930
            PRINT_STRING_NOW "MARCADOR DESATIVADO." 1000
            WAIT 500
        ENDIF
    ENDIF

    IF IS_INT_LVAR_EQUAL_TO_NUMBER ativador 1 
        contador = 0
        WHILE contador < 6 
            IF GET_RANDOM_CHAR_IN_SPHERE_NO_SAVE_RECURSIVE 0.0 0.0 0.0 3000000.0 TRUE FALSE char
                GET_CHAR_COORDINATES char x y z
                CREATE_OBJECT 1930 x y z rastreador
                ATTACH_OBJECT_TO_CHAR rastreador char 0.0 0.0 0.0 0.0 0.0 0.0
                ADD_BLIP_FOR_OBJECT rastreador blip
                CHANGE_BLIP_COLOUR blip 0xff0000FF
                CHANGE_BLIP_SCALE blip 2
                MARK_OBJECT_AS_NO_LONGER_NEEDED rastreador
                WAIT 0
                contador++
            ENDIF
            
            IF IS_KEY_PRESSED 220
            AND IS_KEY_PRESSED 221
                contador = 0
                GOTO desativar
            ENDIF    
            
            WAIT 0
        ENDWHILE
        WAIT 1000
    ENDIF

    WAIT 0
    GOTO loop
}
SCRIPT_END    