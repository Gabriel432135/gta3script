SCRIPT_START
{
NOP
        
LVAR_INT player carro contador modelo statusporta cor numero blip
GET_PLAYER_CHAR 0 player
LVAR_FLOAT xp yp zp x y z zpouso
LVAR_INT modo carroplayer
modo = 0

loop_principal:

//Setar os modos
IF IS_KEY_PRESSED 33//pageup
    IF modo = 0
        modo = 1
        PRINT_STRING_NOW "Hack: Os veiculos vao seguir voce" 1000
        WAIT 500
    ELSE    
        IF modo = 1
            modo = 2
            PRINT_STRING_NOW "Hack: Os avioes e helicopteros vao seguir voce com armas secundarias" 1000
            WAIT 500
        ELSE
            modo = 0
            PRINT_STRING_NOW "Hack: Os veiculos vao ao ponto marcado" 1000
            WAIT 500
        ENDIF    
    ENDIF
ENDIF

IF IS_KEY_PRESSED VK_NEXT
    z=90000.0 //Valor aleatório, apenas para verificar se o ponto no mapa existe e setou as variáveis
    GET_TARGET_BLIP_COORDS x y z
    IF NOT IS_FLOAT_LVAR_EQUAL_TO_NUMBER z 90000.0
        GET_CHAR_COORDINATES player xp yp zp
        contador=0
        WHILE contador<30
            volta_loop:
            IF GET_RANDOM_CAR_IN_SPHERE_NO_SAVE_RECURSIVE xp yp zp 1000.0 TRUE TRUE carro
                //-------Para controlar se afeta o carro do player ou não-----
                IF IS_CHAR_IN_ANY_CAR player
                    GET_CAR_CHAR_IS_USING player carroplayer
                ENDIF
                IF carro = carroplayer
                AND NOT IS_KEY_PRESSED VK_KEY_0
                    GOTO volta_loop
                ENDIF
                //-------------------------------------------------------------

                FIX_CAR carro 
                GET_CAR_DOOR_LOCK_STATUS carro statusporta
                IF NOT statusporta = 0
                    GOTO escolhercor
                    volta:
                    ADD_BLIP_FOR_CAR carro blip
                    CHANGE_BLIP_COLOUR blip cor
                ENDIF 

                GET_CAR_MODEL carro modelo
                IF IS_THIS_MODEL_A_HELI modelo
                SET_VEHICLE_TO_FADE_IN carro 0
                HELI_GOTO_COORDS carro x y z 0.0 100.0  
                zpouso = z+10.0                
                    IF modo = 0
                        IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO
                            IF IS_KEY_PRESSED VK_KEY_9 //fazer o heli pousar
                                HELI_LAND_AT_COORDS carro x y z -100.0 zpouso
                            ELSE
                                HELI_GOTO_COORDS carro x y z z 500.0
                            ENDIF 
                            SET_CAR_CRUISE_SPEED carro 100.0
                            SET_HELI_STABILISER carro TRUE
                        ELSE
                            IF IS_KEY_PRESSED VK_KEY_9 //fazer o heli pousar
                                HELI_LAND_AT_COORDS carro x y z -100.0 zpouso
                            ELSE
                                zpouso=z+90.0
                                HELI_GOTO_COORDS carro x y z z zpouso
                            ENDIF 
                            SET_CAR_CRUISE_SPEED carro 50.0
                            SET_HELI_STABILISER carro TRUE
                        ENDIF
                    ELSE
                        //Configura armas pelo modo---------------
                        IF modo = 1
                            SELECT_WEAPONS_FOR_VEHICLE carro TRUE
                            SET_HELI_STABILISER carro FALSE
                        ELSE
                            SELECT_WEAPONS_FOR_VEHICLE carro FALSE
                            SET_HELI_STABILISER carro FALSE
                        ENDIF
                        //-----------------------------------------
                        
                        IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO
                            HELI_ATTACK_PLAYER carro 0 15.0
                            SET_CAR_CRUISE_SPEED carro 100.0
                        ELSE
                            HELI_FOLLOW_ENTITY carro player -1 10.0
                            SET_CAR_CRUISE_SPEED carro 100.0
                        ENDIF    
                    ENDIF      
                ELSE
                    IF IS_THIS_MODEL_A_PLANE modelo
                        SET_VEHICLE_TO_FADE_IN carro 0   
                        SET_CAR_COLLISION carro FALSE
                        SET_CAR_FORWARD_SPEED carro 50.0
                        PLANE_STARTS_IN_AIR carro
                        PLANE_GOTO_COORDS carro x y z z 150.0
                        IF modo = 0
                            IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO 
                                SET_CAR_CRUISE_SPEED carro 300.0
                            ELSE
                                SET_CAR_CRUISE_SPEED carro 100.6
                            ENDIF
                        ELSE
                            //Configura armas pelo modo---------------
                            IF modo = 1
                                SELECT_WEAPONS_FOR_VEHICLE carro TRUE
                            ELSE
                                SELECT_WEAPONS_FOR_VEHICLE carro FALSE
                            ENDIF
                            //----------------------------------------- 
                            IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO
                                PLANE_ATTACK_PLAYER carro 0 10.0
                                SET_CAR_CRUISE_SPEED carro 300.0
                            ELSE
                                PLANE_FOLLOW_ENTITY carro player -1 10.0
                                SET_CAR_CRUISE_SPEED carro 300.0
                            ENDIF
                        ENDIF
                        SET_CAR_COLLISION carro TRUE
                        PRINT_STRING_NOW "VOOU" 3000
                    ELSE 
                        IF IS_THIS_MODEL_A_BOAT modelo
                            BOAT_GOTO_COORDS carro x y z
                            IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO
                                SET_BOAT_CRUISE_SPEED carro 100.0
                            ELSE
                                SET_BOAT_CRUISE_SPEED carro 30.0
                            ENDIF
                        ELSE
                            CAR_GOTO_COORDINATES carro x y z        
                            IF modo = 0
                                SET_TRAIN_CRUISE_SPEED carro 100.0
                                
                                IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO
                                    SET_CAR_DRIVING_STYLE carro 2 
                                    SET_CAR_CAN_GO_AGAINST_TRAFFIC carro TRUE
                                    SET_CAR_CRUISE_SPEED carro 30.0
                                ELSE
                                    SET_CAR_DRIVING_STYLE carro DRIVINGMODE_STOPFORCARS 
                                    SET_CAR_CAN_GO_AGAINST_TRAFFIC carro FALSE
                                    SET_CAR_CRUISE_SPEED carro 15.0
                                ENDIF
                            ELSE
                                IF IS_KEY_PRESSED VK_KEY_N //AGRESSIVO
                                    SET_CAR_DRIVING_STYLE carro 2 
                                    SET_CAR_CAN_GO_AGAINST_TRAFFIC carro TRUE
                                    SET_CAR_CRUISE_SPEED carro 50.0
                                    IF IS_CHAR_IN_ANY_CAR player
                                        GET_CAR_CHAR_IS_USING player carroplayer
                                        SET_CAR_FOLLOW_CAR carro carroplayer 1.0
                                    ENDIF
                                ELSE
                                    SET_CAR_DRIVING_STYLE carro DRIVINGMODE_STOPFORCARS_IGNORELIGHTS 
                                    SET_CAR_CAN_GO_AGAINST_TRAFFIC carro TRUE
                                    SET_CAR_CRUISE_SPEED carro 30.0
                                    IF IS_CHAR_IN_ANY_CAR player
                                        GET_CAR_CHAR_IS_USING player carroplayer
                                        SET_CAR_FOLLOW_CAR carro carroplayer 10.0
                                    ENDIF
                                ENDIF
                            ENDIF    
                        ENDIF    
                    ENDIF
                ENDIF
                LOCK_CAR_DOORS carro 0 //Apenas para servir de marcação para os blips
                PRINT_STRING_NOW "Veiculos hackeados" 3000         
                                
            ENDIF
            WAIT 0
            contador++
        ENDWHILE
    ELSE
        GET_CHAR_COORDINATES player xp yp zp
        contador=0
        WHILE contador<30
            IF GET_RANDOM_CAR_IN_SPHERE_NO_SAVE_RECURSIVE xp yp zp 1000.0 TRUE TRUE carro
                GET_CAR_DOOR_LOCK_STATUS carro statusporta
                IF NOT statusporta = 0
                    GOTO escolhercor
                    voltab:
                    ADD_BLIP_FOR_CAR carro blip
                    CHANGE_BLIP_COLOUR blip cor
                    LOCK_CAR_DOORS carro 0 //Apenas para servir de marcação para os blips
                    PRINT_STRING_NOW "Sem ponto. Veiculos apenas marcados" 3000    
                ENDIF
            ENDIF
            WAIT 0
            contador++
        ENDWHILE     
    ENDIF               
ENDIF
WAIT 0
GOTO loop_principal

escolhercor:
GENERATE_RANDOM_INT_IN_RANGE 0 6 numero
IF numero=0
    cor=0xFF0009FF
ELSE
    IF numero = 1
        cor = 0xFF7300FF
    ELSE
        IF numero = 2
            cor = 0x11FF00FF
        ELSE
            IF numero = 3
                cor = 0x1100FFFF
            ELSE
                IF numero = 3
                    cor = 0xEA00FFFF
                ELSE
                    IF numero = 4
                        cor = 0xFFFFFFFF
                    ELSE
                        IF numero = 5
                            cor = 0xFF00BFFF
                        ELSE
                            IF numero = 6
                                cor = 0x00FFFFFF
                            ENDIF
                        ENDIF
                    ENDIF
                ENDIF
            ENDIF
        ENDIF
    ENDIF
ENDIF
z=90000.0 //Valor aleatório, apenas para verificar se o ponto no mapa existe e setou as variáveis
GET_TARGET_BLIP_COORDS x y z
IF NOT z=90000.0
    GOTO volta
ELSE
    GOTO voltab
ENDIF  

}
SCRIPT_END