/*
    Autor: Gabriel Alves (Gabriel432135)
*/
SCRIPT_START
{
LVAR_INT player aviao
LVAR_FLOAT xp yp zp
GET_PLAYER_CHAR 0 player    
loop:
IF IS_KEY_PRESSED VK_KEY_G
    GET_CHAR_COORDINATES player xp yp zp
    IF GET_RANDOM_CAR_IN_SPHERE_NO_SAVE_RECURSIVE xp yp zp 100.0 TRUE TRUE aviao
        ATTACH_CHAR_TO_CAR player aviao 0.0 0.0 0.0 FACING_FORWARD 360.0 WEAPONTYPE_CAMERA        
    ENDIF
ENDIF
WAIT 0
GOTO loop
}
SCRIPT_END