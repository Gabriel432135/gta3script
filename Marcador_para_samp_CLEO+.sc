/*
    Autor: Gabriel Alves (Gabriel432135)
*/

SCRIPT_START
{
NOP

LVAR_INT char ativador blip player selo
GET_PLAYER_CHAR 0 player

IF IS_INT_LVAR_EQUAL_TO_CONSTANT ativador 40 //impossível, apenas para enganar o compilador
    GET_PLAYER_CHAR 0 char
ENDIF

loop_principal:
WAIT 0 
IF IS_KEY_PRESSED 220
AND IS_KEY_PRESSED 221
    IF ativador = 0
        ativador = 1
        PRINT_STRING_NOW "MARCADOR ATIVADO." 1000
        WAIT 500
    ELSE
        ativador = 0
        PRINT_STRING_NOW "MARCADOR DESATIVADO." 1000
        WAIT 500
    ENDIF
    GOTO loopEscolha
ENDIF
//--------------------------------------------------------

GOTO loop_principal

loopEscolha:
IF ativador = 1
    SET_SCRIPT_EVENT_CHAR_PROCESS ON darblip char 
    WAIT 0
    SET_SCRIPT_EVENT_CHAR_CREATE ON darblip char 
ELSE
    SET_SCRIPT_EVENT_CHAR_PROCESS OFF darblip char 
    WAIT 0
    SET_SCRIPT_EVENT_CHAR_CREATE OFF darblip char 
ENDIF

GOTO loop_principal

darblip:
    IF NOT IS_INT_LVAR_EQUAL_TO_INT_LVAR player char
        IF NOT GET_EXTENDED_CHAR_VAR char AUTO 1 selo //para marcar que ele já foi
            ADD_BLIP_FOR_CHAR char blip
            CHANGE_BLIP_COLOUR blip 0xff8200FF
            CHANGE_BLIP_SCALE blip 2 
            INIT_EXTENDED_CHAR_VARS char AUTO 1
        ENDIF    
    ENDIF    
RETURN_SCRIPT_EVENT

}
SCRIPT_END    