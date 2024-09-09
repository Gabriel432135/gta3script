SCRIPT_START
{
LVAR_INT char anim
LVAR_INT entidade tipodearma damagepiece
LVAR_FLOAT damageintencidade

GET_PLAYER_CHAR 0 char

SET_SCRIPT_EVENT_CHAR_DAMAGE TRUE principal char

GOTO fim //Para pular o loop "Principal"

principal:
GET_CHAR_DAMAGE_LAST_FRAME char entidade tipodearma damagepiece damageintencidade 
IF IS_INT_LVAR_EQUAL_TO_CONSTANT tipodearma 8 //Katana
    IF NOT IS_CHAR_HEAD_MISSING char
        GENERATE_RANDOM_INT_IN_RANGE 1 4 anim
        SWITCH anim
            CASE 1
                TASK_DIE_NAMED_ANIM char "KO_shot_face" "ped" 4.0 -1
                BREAK
            CASE 2
                TASK_DIE_NAMED_ANIM char "KO_shot_stom" "ped" 4.0 -1
                BREAK
        ENDSWITCH
        EXPLODE_CHAR_HEAD char //Confirmar a morte
    ENDIF
ENDIF
RETURN_SCRIPT_EVENT

fim:

}

SCRIPT_END