/*
 by Gabriel Alves
 Gabriel432135
*/

SCRIPT_START
{
NOP

WAIT 0 

LVAR_INT char 
LVAR_FLOAT x
LVAR_FLOAT y
LVAR_FLOAT z
LVAR_INT numeroaleatorio resset
LVAR_INT jogador fogo carro carrocj ativado modelo
LVAR_INT fogocj fogomd fogome fogope fogopd

GET_PLAYER_CHAR 0 jogador

main_loop:    

WAIT 0

IF IS_KEY_PRESSED VK_KEY_T
AND IS_KEY_PRESSED VK_KEY_0

    IF IS_CHAR_SITTING_IN_ANY_CAR jogador
        GET_CAR_CHAR_IS_USING jogador carrocj
        SET_CAR_PROOFS carrocj TRUE TRUE TRUE TRUE TRUE //deixa o carro do cj indestrutível
    ENDIF

    IF ativado = FALSE
        ativado = TRUE

        //CRIANDO FOGO NA CABEÇA----------------------------------------------  
        CREATE_FX_SYSTEM_ON_CHAR "fire_bike" jogador 0.4 -0.4 0.0 0 fogocj
        ATTACH_FX_SYSTEM_TO_CHAR_BONE fogocj jogador 5
        PLAY_FX_SYSTEM fogocj
        //------------------------------------------------------------------

        //CRIANDO FOGO NA MÃO DIREITA----------------------------------------------  
        CREATE_FX_SYSTEM_ON_CHAR_WITH_DIRECTION "molotov_flame" jogador 0.0 0.0 0.0 0.0 0.0 90.0 0 fogomd
        ATTACH_FX_SYSTEM_TO_CHAR_BONE fogomd jogador 24
        PLAY_FX_SYSTEM fogomd
        //------------------------------------------------------------------

        //CRIANDO FOGO NA MÃO ESQUERDA----------------------------------------------  
        CREATE_FX_SYSTEM_ON_CHAR_WITH_DIRECTION "molotov_flame" jogador 0.0 0.0 0.0 0.0 0.0 180.0 0 fogome
        ATTACH_FX_SYSTEM_TO_CHAR_BONE fogome jogador 34
        PLAY_FX_SYSTEM fogome
        //------------------------------------------------------------------  

        //CRIANDO FOGO N0 PÉ DIREIT0----------------------------------------------  
        CREATE_FX_SYSTEM_ON_CHAR_WITH_DIRECTION "molotov_flame" jogador 0.0 0.0 0.0 0.0 0.0 270.0 0 fogopd
        ATTACH_FX_SYSTEM_TO_CHAR_BONE fogopd jogador 53
        PLAY_FX_SYSTEM fogopd
        //------------------------------------------------------------------

        //CRIANDO FOGO N0 PÉ ESQUERDO----------------------------------------------  
        CREATE_FX_SYSTEM_ON_CHAR_WITH_DIRECTION "molotov_flame" jogador 0.0 0.0 0.0 0.0 0.0 0.0 0 fogope
        ATTACH_FX_SYSTEM_TO_CHAR_BONE fogope jogador 43
        PLAY_FX_SYSTEM fogope
        //------------------------------------------------------------------  



        SET_CHAR_PROOFS jogador TRUE TRUE TRUE TRUE TRUE
        PRINT_STRING_NOW "MODO ANJO DA MORTE ATIVADO" 3000
        WAIT 500
    ELSE
        ativado = FALSE
        //DESLIGANDO AS ANIMAÇÕES--------------------------- 
        KILL_FX_SYSTEM fogocj
        KILL_FX_SYSTEM fogomd
        KILL_FX_SYSTEM fogome
        KILL_FX_SYSTEM fogopd
        KILL_FX_SYSTEM fogope
        //--------------------------------------------------

        SET_CHAR_PROOFS jogador FALSE FALSE FALSE FALSE FALSE
        PRINT_STRING_NOW "MODO ANJO DA MORTE DESATIVADO" 3000
        WAIT 500
    ENDIF
ENDIF

WAIT 0

IF ativado = TRUE
GOTO loops
ENDIF

GOTO main_loop

loops:
    GET_CHAR_COORDINATES jogador x y z
    SET_CHAR_PROOFS jogador TRUE TRUE TRUE TRUE TRUE //Função colocada dentro do laço, porque ela se desativa em algumas situações

    IF GET_RANDOM_CHAR_IN_SPHERE_NO_SAVE_RECURSIVE x y z 30.0 (TRUE TRUE) char
        WAIT 0

        GENERATE_RANDOM_INT_IN_RANGE 0 100 numeroaleatorio
        IF DOES_CHAR_EXIST char //segurança pra não crashar
        
            IF IS_INT_LVAR_GREATER_THAN_NUMBER numeroaleatorio 70 // 30% de chance
                SET_CHAR_HEALTH char 0
                EXPLODE_CHAR_HEAD char
                WAIT 0
            ELSE 
                START_CHAR_FIRE char fogo
                resset++
                IF IS_INT_LVAR_GREATER_THAN_CONSTANT resset 30
                    REMOVE_ALL_SCRIPT_FIRES
                    resset=0
                ENDIF
                WAIT 0
            ENDIF
        ENDIF//-----------------------------------------------

        WAIT 0
    ENDIF

    IF GET_RANDOM_CAR_IN_SPHERE_NO_SAVE_RECURSIVE x y z 70.0 (TRUE TRUE) carro
        WAIT 0
            IF DOES_VEHICLE_EXIST carro //segurança pra não crashar
                IF NOT IS_INT_LVAR_EQUAL_TO_INT_LVAR carro carrocj //Para não afetar o carro do cj
                    GET_CAR_MODEL carro modelo
                    IF IS_THIS_MODEL_A_PLANE modelo
                        EXPLODE_CAR carro
                    ELSE 
                        SET_CAR_HEALTH carro 0
                        SET_CAR_ENGINE_BROKEN carro TRUE
                        MAKE_HELI_COME_CRASHING_DOWN carro //Se for um helicoptero, ele já começa a rodar e cair
                    ENDIF
                ENDIF    
            ENDIF//---------------------------------------------------
            
        WAIT 0
    ENDIF    
WAIT 0
GOTO main_loop

}
SCRIPT_END