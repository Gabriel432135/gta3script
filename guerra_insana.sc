/*
    Autor: Gabriel Alves (Gabriel432135)
*/
SCRIPT_START
{
NOP

LVAR_INT player
LVAR_FLOAT x y z
LVAR_INT ajuda inimigo armaplayer modeloarmaplayer
LVAR_INT tipodeluta blip modelorandom
LVAR_INT ativador
tipodeluta = 4

main_loop:

WAIT 0

//--------------------Pegando o estilo de luta--------------------------------------------------
IF IS_KEY_PRESSED VK_KEY_T
    WAIT 0
    IF IS_KEY_PRESSED VK_KEY_4
        tipodeluta=4
        PRINT_STRING_NOW "(Guerra insana) Estilo de luta alterado para Normal" 1000
    ELSE
        IF IS_KEY_PRESSED VK_KEY_5
            tipodeluta=5
            PRINT_STRING_NOW "(Guerra insana) Estilo de luta alterado para Boxe" 1000
        ELSE
            IF IS_KEY_PRESSED VK_KEY_6
                tipodeluta=6
                PRINT_STRING_NOW "(Guerra insana) Estilo de luta alterado para Kung Fu" 1000
            ELSE
                IF IS_KEY_PRESSED VK_KEY_7
                    tipodeluta=7
                    PRINT_STRING_NOW "(Guerra insana) Estilo de luta alterado para Chute Boxe" 1000
                ELSE
                    IF IS_KEY_PRESSED VK_KEY_8
                        tipodeluta=8
                        PRINT_STRING_NOW "(Guerra insana) Estilo de luta alterado para Sei la" 1000
                    ELSE
                        IF IS_KEY_PRESSED VK_KEY_9
                            tipodeluta=15
                            PRINT_STRING_NOW "(Guerra insana) Estilo de luta alterado para O Estilo CJ" 1000
                        ENDIF
                    ENDIF
                ENDIF
            ENDIF
        ENDIF
    ENDIF
ENDIF    
//-------------------------------------------------------------------------------------------------------
WAIT 0

IF ativador = 1 //Se o mod está ativado
    IF IS_KEY_PRESSED VK_KEY_L
    GET_PLAYER_CHAR 0 player
        GET_CHAR_COORDINATES player x y z

        WAIT 0
        IF IS_KEY_PRESSED VK_KEY_N //CRIAR CAPITÃO ALIADO ---------------------------------------
            REQUEST_MODEL 70
            WHILE NOT HAS_MODEL_LOADED 70
                WAIT 0
            ENDWHILE

            CREATE_CHAR 8 70 x y z ajuda
            MARK_CHAR_AS_NO_LONGER_NEEDED ajuda
            MARK_MODEL_AS_NO_LONGER_NEEDED 70
            SET_CHAR_HEALTH ajuda 90000
            SET_CHAR_ACCURACY ajuda 100
            SET_CHAR_WEAPON_SKILL ajuda 2
            SET_CHAR_SHOOT_RATE ajuda 100
            PRINT_STRING_NOW "CHEFE ALIADO CRIADO" 3000
        ELSE //CRIAR SOLDADA ALIADA ---------------------------------------    
            IF IS_KEY_PRESSED VK_KEY_M

                GOTO escolher_modelo_soldada
                continua:
                REQUEST_MODEL modelorandom
                WHILE NOT HAS_MODEL_LOADED modelorandom
                    WAIT 0
                ENDWHILE

                CREATE_CHAR 8 modelorandom x y z ajuda
                MARK_CHAR_AS_NO_LONGER_NEEDED ajuda
                MARK_MODEL_AS_NO_LONGER_NEEDED modelorandom
                PRINT_STRING_NOW "ALIADA CRIADA" 3000

            ELSE //CRIAR SOLDADO ALIADO --------------------------------------- 
                IF IS_KEY_PRESSED 186 //ç   
                    REQUEST_MODEL 285
                    WHILE NOT HAS_MODEL_LOADED 285
                        WAIT 0
                    ENDWHILE

                    CREATE_CHAR 8 285 x y z ajuda
                    MARK_CHAR_AS_NO_LONGER_NEEDED ajuda
                    MARK_MODEL_AS_NO_LONGER_NEEDED 285

                    PRINT_STRING_NOW "SOLDADO ALIADO CRIADO" 3000
                ELSE//CRIANDO GROVE ALIADO-------------------------------------------
                    GOTO escolher_modelo_grove
                    volta_grove:
                    REQUEST_MODEL modelorandom
                    WHILE NOT HAS_MODEL_LOADED modelorandom
                        WAIT 0
                    ENDWHILE

                    CREATE_CHAR 8 modelorandom x y z ajuda
                    MARK_CHAR_AS_NO_LONGER_NEEDED ajuda
                    MARK_MODEL_AS_NO_LONGER_NEEDED modelorandom
                    PRINT_STRING_NOW "ALIADO CRIADO" 3000
                ENDIF    
            ENDIF
        ENDIF //-------------------------------------------

        CLEAR_CHAR_TASKS ajuda
        SET_CHAR_DECISION_MAKER ajuda 0 

        WAIT 0
        //ENTREGANDO A ARMA DO PLAYER------------------------
        GET_CURRENT_CHAR_WEAPON player armaplayer
        GET_WEAPONTYPE_MODEL armaplayer modeloarmaplayer
        REQUEST_MODEL modeloarmaplayer
        WHILE NOT HAS_MODEL_LOADED modeloarmaplayer
            WAIT 0
        ENDWHILE
        GIVE_WEAPON_TO_CHAR ajuda armaplayer 999999
        SET_CURRENT_CHAR_WEAPON ajuda armaplayer
        MARK_MODEL_AS_NO_LONGER_NEEDED modeloarmaplayer
        WAIT 0
        //------------------------------------------------

        //APLICANDO O ESTILO DE LUTA----------------------
        GIVE_MELEE_ATTACK_TO_CHAR ajuda tipodeluta 15
        //------------------------------------------------    
        
    ENDIF

    IF IS_KEY_PRESSED VK_KEY_B
        
        GET_PLAYER_CHAR 0 player
        GET_CHAR_COORDINATES player x y z

        WAIT 0
        IF IS_KEY_PRESSED VK_KEY_N //CRIAR CAPITÃO INIMIGO---------------------------------------
            REQUEST_MODEL 68
            WHILE NOT HAS_MODEL_LOADED 68
                WAIT 0
            ENDWHILE

            CREATE_CHAR 7 68 x y z inimigo
            MARK_CHAR_AS_NO_LONGER_NEEDED inimigo
            MARK_MODEL_AS_NO_LONGER_NEEDED 68
            SET_CHAR_HEALTH inimigo 90000
            SET_CHAR_ACCURACY inimigo 100
            SET_CHAR_WEAPON_SKILL inimigo 2 
            SET_CHAR_SHOOT_RATE inimigo 100
            PRINT_STRING_NOW "CHEFE INIMIGO CRIADO" 3000
        ELSE //CRIAR SOLDADO INIMIGO---------------------------------------
            GOTO escolher_modelo_balla
            volta_balla:
            REQUEST_MODEL modelorandom
            WHILE NOT HAS_MODEL_LOADED modelorandom
                WAIT 0
            ENDWHILE

            CREATE_CHAR 7 modelorandom x y z inimigo
            MARK_CHAR_AS_NO_LONGER_NEEDED inimigo
            MARK_MODEL_AS_NO_LONGER_NEEDED 104
            PRINT_STRING_NOW "INIMIGO CRIADO" 3000
        ENDIF //-------------------------------------------

        CLEAR_CHAR_TASKS inimigo
        SET_CHAR_DECISION_MAKER inimigo 0
        
        WAIT 0

        //ENTREGANDO A ARMA DO PLAYER------------------------
        GET_CURRENT_CHAR_WEAPON player armaplayer
        GET_WEAPONTYPE_MODEL armaplayer modeloarmaplayer
        REQUEST_MODEL modeloarmaplayer
        WHILE NOT HAS_MODEL_LOADED modeloarmaplayer
            WAIT 0
        ENDWHILE
        GIVE_WEAPON_TO_CHAR inimigo armaplayer 999999
        SET_CURRENT_CHAR_WEAPON inimigo armaplayer
        MARK_MODEL_AS_NO_LONGER_NEEDED modeloarmaplayer
        WAIT 0
        //---------------------------------------------------

        //APLICANDO O ESTILO DE LUTA----------------------
        GIVE_MELEE_ATTACK_TO_CHAR inimigo tipodeluta 15
        //------------------------------------------------       

    ENDIF

ENDIF

GOSUB ativar_desativar 

WAIT 0 


GOTO main_loop





//Escolhe aleatoriamente um dos modelos listados para a soldada aliada
escolher_modelo_soldada:
    GENERATE_RANDOM_INT_IN_RANGE 0 7 modelorandom
    IF modelorandom =0
        modelorandom=138
    ELSE
        IF modelorandom =1
            modelorandom=141
        ELSE
            IF modelorandom =2
                modelorandom=150
            ELSE
                IF modelorandom =3
                    modelorandom=172
                ELSE
                    IF modelorandom =4
                        modelorandom=90
                    ELSE
                        IF modelorandom =5
                            modelorandom=92
                        ELSE
                            modelorandom=140
                        ENDIF
                    ENDIF
                ENDIF
            ENDIF
        ENDIF
    ENDIF
    GOTO continua

//-------------------------------------------------------------

//Escolhe aleatoriamente um dos modelos listados para o balla inimigo   
escolher_modelo_balla:
    GENERATE_RANDOM_INT_IN_RANGE 102 105 modelorandom 
    //como o modelo dos ballas está em sequência, isso facilita as coisas
    GOTO volta_balla
//-------------------------------------------------------------



//Escolhe aleatoriamente um dos modelos listados para o grove aliado   
escolher_modelo_grove:
    GENERATE_RANDOM_INT_IN_RANGE 105 108 modelorandom 
    //como o modelo dos groves está em sequência, isso facilita as coisas
    GOTO volta_grove
//-------------------------------------------------------------

//Ativa e desativa o mod----------------------------------------
ativar_desativar:
IF TEST_CHEAT "GUERRA"
    IF ativador = 0
        ativador = 1
        PRINT_STRING_NOW "(Guerra insana) ATIVADO" 1500
    ELSE
        ativador = 0
        PRINT_STRING_NOW "(Guerra insana) DESATIVADO" 1500
    ENDIF
ENDIF
RETURN
//---------------------------------------------------------------

} 
SCRIPT_END