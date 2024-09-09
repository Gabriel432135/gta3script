/*
    Autor: Gabriel Alves (Gabriel432135)
*/
SCRIPT_START
{
NOP

LVAR_INT ped_alvo
LVAR_INT objeto
LVAR_INT pedestres
LVAR_INT player
LVAR_INT contador
LVAR_FLOAT x xc xp
LVAR_FLOAT y yc yp
LVAR_FLOAT z zc zp
LVAR_FLOAT distancia

WAIT 0

loop:
WAIT 0
IF IS_KEY_PRESSED VK_KEY_O
    IF GET_CHAR_PLAYER_IS_TARGETING 0 ped_alvo

    SET_CHAR_IS_TARGET_PRIORITY ped_alvo TRUE

    GET_PLAYER_CHAR 0 player

    GET_CHAR_COORDINATES player x y z
        //Para pedestres
        PRINT_STRING_NOW "MARCANDO..." 10000
        IF GET_RANDOM_CHAR_IN_SPHERE_NO_SAVE_RECURSIVE x y z 100.0 TRUE TRUE pedestres
            WHILE contador<30
                GET_RANDOM_CHAR_IN_SPHERE_NO_SAVE_RECURSIVE x y z 100.0 TRUE TRUE pedestres
                IF IS_INT_LVAR_EQUAL_TO_INT_LVAR ped_alvo pedestres
                    WAIT 0
                ELSE
                    TASK_KILL_CHAR_ON_FOOT pedestres ped_alvo
                    WAIT 0
                ENDIF
                contador++
            ENDWHILE 
        ENDIF
        REMOVE_ALL_CHAR_WEAPONS ped_alvo
        PRINT_STRING_NOW "PEDESTRE MARCADO by gabriel432135" 3000
        contador=0
    ELSE
        WAIT 0 
        GET_PLAYER_CHAR 0 player
        GET_CHAR_COORDINATES player x y z
        //Para veículos
        IF GET_RANDOM_CAR_IN_SPHERE_NO_SAVE_RECURSIVE x y z 5.0 TRUE TRUE objeto
            WAIT 200
            IF IS_KEY_PRESSED VK_KEY_O
                SET_CAR_ENGINE_BROKEN objeto TRUE
            ENDIF    
            PRINT_STRING_NOW "MARCANDO..." 10000 
            //Pegar coordenadas do carro pra usar abaixo, na parte do pedestre pular em cima
            GET_CAR_COORDINATES objeto xc yc zc 
            //------------------------------------------------------------------------------
                WHILE contador<30
                    IF GET_RANDOM_CHAR_IN_SPHERE_NO_SAVE_RECURSIVE x y z 100.0 TRUE TRUE pedestres
                        IF IS_CHAR_IN_CAR pedestres objeto
                            WAIT 0
                        ELSE
                            //Pro pedestre pular, para subir em cima do carro
                            IF NOT IS_CHAR_SITTING_IN_ANY_CAR pedestres
                                GET_CHAR_COORDINATES pedestres xp yp zp
                                GET_DISTANCE_BETWEEN_COORDS_3D xc yc zc xp yp zp distancia
                                IF IS_NUMBER_GREATER_THAN_FLOAT_LVAR 5.0 distancia
                                    TASK_JUMP pedestres FALSE
                                    WAIT 0
                                ENDIF
                            ENDIF                           
                            //-----------------------------------------------
                            TASK_DESTROY_CAR pedestres objeto
                            WAIT 0
                        ENDIF
                    ENDIF        
                    contador++
                    WAIT 0
                ENDWHILE 
            PRINT_STRING_NOW "VEICULO MARCADO by gabriel432135" 3000
            contador=0
        ENDIF

    ENDIF
ENDIF    
GOTO loop
}
SCRIPT_END
