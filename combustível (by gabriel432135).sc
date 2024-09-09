SCRIPT_START
{

/*
    Autor: Gabriel Alves (Gabriel432135)
*/

NOP
LVAR_INT ativador player combustivel adaptador motorista carro ponteiro tipo_de_veiculo modelo_veiculo volta subvolta blip teste resposta
LVAR_INT pickup vslot save
LVAR_FLOAT acelerador

SET_SCRIPT_EVENT_SAVE_CONFIRMATION ON configurar_save vslot

GOSUB criar_pickups

GOTO dar_blips_avioes
volta:

GOTO dar_blips_carros
volta0:


//Variável "adaptador" serve para permitir que o valor de combustível seja mostrado corretamente na barra que tem 100 como valor máximo
/*Esse mod simula que os carros andam aproximadamente 0,47 km/l, e que os carros têm 5l no tanque. É um valor pequeno, mas para não ficar
entediante. Também não sei se as medições de distância, em metros, estão corretas. Teste feito com o Admiral. Não foi levado em consideração
a potência de cada motor*/

GET_PLAYER_CHAR 0 player
ativador = 1

loop_principal:

volta = 15
GOTO ativar_desativar
volta15:

IF save = 1 //Para recriar os pickups quando salva o jogo
    GOSUB criar_pickups
    save = 0
    IF teste = 1 //Código no modo de teste
        PRINT_STRING_NOW "Pickups recriados" 1000
    ENDIF
ENDIF

IF IS_INT_LVAR_EQUAL_TO_CONSTANT ativador 1 
AND NOT IS_ON_MISSION

    CLEO_CALL esta_em_algum_carro_valido 0 (player)(carro) (resposta)
    IF resposta = TRUE
        GET_CAR_CHAR_IS_USING player carro

        GET_VEHICLE_POINTER carro ponteiro
        GET_CAR_MODEL carro modelo_veiculo

        volta=1 //Variável para indicar para onde o script deve voltar
        GOTO verificar_tipo_de_veiculo
        volta1:

        IF tipo_de_veiculo = 0 //carro/avião/heli
        OR tipo_de_veiculo = 9 //bike/moto
        IF NOT IS_INT_LVAR_EQUAL_TO_CONSTANT modelo_veiculo 481 //Não é uma bmx
        AND NOT IS_INT_LVAR_EQUAL_TO_CONSTANT modelo_veiculo 509 //Não é uma bike antiga
        AND NOT IS_INT_LVAR_EQUAL_TO_CONSTANT modelo_veiculo 510 //Não é uma montain bike

            //Criando o valor e guardando  no carro, caso não tenha um
            IF NOT GET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
                INIT_EXTENDED_CAR_VARS carro "combustivel" 1                
                //Ainda não sei como salvar o combustivel do carro na garagem e no save :(*/
                GENERATE_RANDOM_INT_IN_RANGE 5000 80000 combustivel
                SET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
            ENDIF

            DISPLAY_ONSCREEN_COUNTER_LOCAL adaptador COUNTER_DISPLAY_BAR //Só para inicializar
            
            //Pegando o valor
            GET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel 

            //===================================================================================================
            WHILE IS_CHAR_SITTING_IN_CAR player carro
            AND combustivel > 0 

                volta = 12
                GOTO verificar_motor_e_ignição
                volta12:

                //Para diferenciar posto de carro com posto de avião
                IF IS_THIS_MODEL_A_PLANE modelo_veiculo
                OR IS_THIS_MODEL_A_HELI modelo_veiculo
                    volta = 8
                    GOTO abastecimento_aeronave
                    volta8:
                ELSE
                    volta=5
                    GOTO posto_carros
                    volta5:
                ENDIF

                volta = 20
                GOTO gastar_combustivel
                volta20:

                //Mostrar na barrinha de forma adaptada pra porcentagem------------
                adaptador = combustivel
                DIV_INT_LVAR_BY_VAL adaptador 1000   
                SET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
                //-----------------------------------------------------------------

                IF teste = 1 //Para testes
                    PRINT_FORMATTED_NOW "%i" 1000 combustivel
                ENDIF

                volta = 16
                GOTO ativar_desativar //COMB
                volta16: 
                
                WAIT 28 //calibragem de fps do mod
            ENDWHILE
            //===================================================================================================

            motor_desligado:

            IF combustivel <= 0 //Nunca é menor que 0, mas vai que...
                volta = 10
                GOTO desligar_veículo
                volta10:
            ENDIF

            //===================================================================================================
            WHILE IS_CHAR_SITTING_IN_CAR player carro //Barrinha vazia esperando para o playes sair do veículo para sumir com sistema para impedir drift com moto desligada//
                volta2:

                //Para diferenciar posto de carro com posto de avião
                IF IS_THIS_MODEL_A_PLANE modelo_veiculo
                OR IS_THIS_MODEL_A_HELI modelo_veiculo
                    volta = 9
                    GOTO abastecimento_aeronave
                    volta9:
                ELSE
                    volta=6
                    GOTO posto_carros
                    volta6:
                ENDIF

                volta = 21
                GOTO impedir_drift_com_moto_desligada
                volta21:

                volta = 22
                GOTO verificar_motor_desligado_e_ignição
                volta22:

                GOSUB desligar_helice_avioes_helicopteros
                
                //Mostrar na barrinha de forma adaptada pra porcentagem------------
                GET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
                adaptador = combustivel
                DIV_INT_LVAR_BY_VAL adaptador 1000   
                SET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
                //-----------------------------------------------------------------

                volta = 17
                GOTO ativar_desativar //COMB
                volta17:

                IF teste = 1 //Para testes
                    PRINT_FORMATTED_NOW "%i" 1000 combustivel
                ENDIF

                WAIT 0 
            ENDWHILE 
            //=========================================================================================================================
            depois_de_tudo:
            DISPLAY_ONSCREEN_COUNTER_LOCAL adaptador COUNTER_DISPLAY_BAR //Só para não crashar caso não exista
            CLEAR_ONSCREEN_COUNTER_LOCAL adaptador
        ENDIF//É um carro, moto ou avião?
        ENDIF//Não é bicicleta?
    ENDIF
ENDIF 
WAIT 0 
GOTO loop_principal







desligar_helice_avioes_helicopteros:
//Tudo isso é para impedir aviões desligados de girarem a hélice e helicopteros desligados de voarem
//(ISSO TUDO COM O JOGADOR APENAS)
IF IS_THIS_MODEL_A_PLANE modelo_veiculo
OR IS_THIS_MODEL_A_HELI modelo_veiculo
    WRITE_STRUCT_OFFSET ponteiro 0x36 1 74  
ENDIF
RETURN

impedir_drift_com_moto_desligada:
//Tudo isso é apenas para impedir que o player dê drift com a moto desligada
IF tipo_de_veiculo = 9
    
    subvolta = 3
    GOTO get_motorista
    volta3:

    IF motorista = player //É o jogador que está pilotando
        IF IS_BUTTON_PRESSED PAD1 CROSS //ACELERADOR //Não está tentando dar drift
        AND IS_BUTTON_PRESSED PAD1 SQUARE //FREIO 
            WRITE_STRUCT_OFFSET ponteiro 0x36 4 0x1A //Não pode dirigir, só pra não dar drift com a modo desligada.   
        ELSE
            WRITE_STRUCT_OFFSET ponteiro 0x36 4 0x02 //Pode dirigir? Pode sim   
        ENDIF
    ENDIF    
ENDIF

IF volta = 21
    GOTO volta21
ENDIF

gastar_combustivel:
//Gastar o combustível de forma inteligente e realista
IF IS_CAR_ENGINE_ON carro
    READ_STRUCT_OFFSET ponteiro 0x49C 4 acelerador //pedal acelerador            
    IF NOT acelerador = 0.0 //Está acelerando
        IF tipo_de_veiculo = 9 //É uma moto
            //Pegar motorista-
            subvolta=4
            GOTO get_motorista
            volta4:
            //----------------
            IF player = motorista
                IF IS_BUTTON_PRESSED PAD1 SQUARE //freiando apenas ou dando ré a pé com a moto (Nunca vi um NPC dando ré de moto...)
                AND NOT IS_BUTTON_PRESSED PAD1 CROSS //Não está acelerando    
                    NOP //Não faça nada
                ELSE
                    combustivel-=2 //Gastar mais quando acelerando
                ENDIF
            ELSE
                combustivel-=2 //Gastar mais quando acelerando
            ENDIF
        ELSE
            combustivel-=2 //Gastar mais quando acelerando
        ENDIF
    ELSE
        IF IS_THIS_MODEL_A_PLANE modelo_veiculo
        OR IS_THIS_MODEL_A_HELI modelo_veiculo
            combustivel-=2 //Avião e helicoptero gasta de forma constante. O pedal de aceleração não funciona com eles
        ENDIF
    ENDIF
    IF NOT modelo_veiculo = 457 //Caddy não gasta energia parado 
        combustivel-- //Só por ele estar ligado. + realismo
    ENDIF
ENDIF            
IF volta = 20
    GOTO volta20
ENDIF



verificar_motor_e_ignição:
IF NOT IS_CAR_ENGINE_ON carro //O motor está desligado?
    volta = 300
    GOTO desligar_veículo
ENDIF

IF IS_KEY_PRESSED VK_END
    subvolta = 13
    GOTO get_motorista
    volta13:
    IF player = motorista
        IF IS_CAR_ENGINE_ON carro //O motor está ligado?
            volta = 2
            GOTO desligar_veículo
        ELSE
            IF combustivel > 0
                subvolta = 11
                GOTO ligar_veículo
                volta11:
            ENDIF
        ENDIF
    ENDIF
ENDIF
IF volta = 12
    GOTO volta12
ENDIF


verificar_motor_desligado_e_ignição:
IF IS_CAR_ENGINE_ON carro //O motor não está desligado? (Manter o motor desligado)
    IF combustivel > 0 
        volta = 12
        GOTO ligar_veículo
    ELSE
        subvolta = 19
        GOTO desligar_veículo
        volta19:
    ENDIF
ENDIF

IF IS_KEY_PRESSED VK_END
    subvolta = 14
    GOTO get_motorista
    volta14:
    IF player = motorista
        WAIT 500
        IF combustivel > 0
            volta = 12
            GOTO ligar_veículo
        ENDIF
    ENDIF
ENDIF 

IF volta = 22
    GOTO volta22
ENDIF


/*
é_carro_eletrico:
GET_CAR_MODEL carro modelo_veiculo
IF modelo_veiculo = 457 //caddy
OR modelo_veiculo = 583 //tug
OR modelo_veiculo = 530 //empilhadeira
OR modelo_veiculo = 485 //baggage
    resposta = TRUE
ENDIF
*/



verificar_tipo_de_veiculo:
IF IS_CHAR_SITTING_IN_CAR player carro
    READ_STRUCT_OFFSET ponteiro 0x590 1 tipo_de_veiculo
ELSE
    GOTO depois_de_tudo
ENDIF
IF volta=1
    GOTO volta1
ENDIF


ativar_desativar:
IF TEST_CHEAT MDC
    IF ativador = 1
        ativador = 0
        PRINT_STRING_NOW "CONSUMO DE COMBUSTIVEL DESATIVADO" 3000
        CLEO_CALL esta_em_algum_carro_valido 0 (player)(carro) (resposta)
        IF resposta = TRUE
            GET_CAR_CHAR_IS_USING player carro //Por segurança
            GET_VEHICLE_POINTER carro ponteiro //Por segurança
            volta = 18
            GOTO ligar_veículo
            volta18:
        ENDIF
        DISPLAY_ONSCREEN_COUNTER_LOCAL adaptador COUNTER_DISPLAY_BAR //Só para não crashar caso não exista
        CLEAR_ONSCREEN_COUNTER_LOCAL adaptador
        GOTO loop_principal
    ELSE
        PRINT_STRING_NOW "CONSUMO DE COMBUSTIVEL ATIVADO" 3000
        ativador = 1
        GOTO loop_principal
    ENDIF 
ENDIF

IF TEST_CHEAT TESTES
    IF teste = 0
        teste = 1
    ELSE
        teste = 0
    ENDIF
ENDIF

IF TEST_CHEAT MIL
    combustivel = 1000
ENDIF

IF TEST_CHEAT "AUTORCOMB"
    PRINT_STRING_NOW "Mod Combustivel - Autor: Gabriel Alves (Gabriel432135)" 10000
ENDIF

SWITCH volta
    CASE 15
        GOTO volta15
        BREAK
    CASE 16
        GOTO volta16
        BREAK
    CASE 17
        GOTO  volta17
        BREAK
ENDSWITCH


get_motorista:
IF IS_CHAR_SITTING_IN_CAR player carro
    GET_DRIVER_OF_CAR carro motorista
ELSE
    GOTO depois_de_tudo
ENDIF
IF subvolta = 3
    GOTO volta3
ENDIF
IF subvolta = 4
    GOTO volta4
ENDIF
IF subvolta = 13
    GOTO volta13
ENDIF
IF subvolta = 14
    GOTO volta14
ENDIF

ligar_veículo:
IF IS_CHAR_SITTING_IN_CAR player carro
    SET_CAR_ENGINE_BROKEN carro FALSE
    SET_CAR_ENGINE_ON carro TRUE
    WRITE_STRUCT_OFFSET ponteiro 0x94 4 1.0 //destravar motos. Se não for moto, não muda nada, pq ddivider já é 1.0
    WRITE_STRUCT_OFFSET ponteiro 0x36 4 0x02 //Destravar pro cj
ELSE
    GOTO depois_de_tudo
ENDIF

IF subvolta = 11
    GOTO volta11
ENDIF

SWITCH volta
    CASE 12
        GOTO volta12
        BREAK
    CASE 18
        GOTO volta18
        BREAK
ENDSWITCH 

desligar_veículo:
IF IS_CHAR_SITTING_IN_CAR player carro
    READ_STRUCT_OFFSET ponteiro 0x590 1 tipo_de_veiculo //tipo de veículo
    IF tipo_de_veiculo = 9 //moto
        WRITE_STRUCT_OFFSET ponteiro 0x94 4 1000000.0 //travar motos gdivider
    ENDIF
    SET_CAR_ENGINE_BROKEN carro TRUE
ELSE
    GOTO depois_de_tudo
ENDIF

IF subvolta = 19
    GOTO volta19
ENDIF

SWITCH volta
    CASE 10
        GOTO volta10
        BREAK
    CASE 2
        WAIT 500
        GOTO volta2
        BREAK
    CASE 300
        GOTO motor_desligado
        BREAK
ENDSWITCH

colocar_combustivel:
WAIT 28
IF IS_CHAR_SITTING_IN_CAR player carro
    IF GET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel 
        IF combustivel <= 99000 //Para ter uma margem
            IF IS_SCORE_GREATER 0 0 //O cj tem dinheiro? Cada ciclo custa $1
                combustivel += 1000
                adaptador = combustivel
                DIV_INT_LVAR_BY_VAL adaptador 1000   
                SET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
                ADD_SCORE 0 -1
                PRINT_FORMATTED_NOW "combustivel em %i/100" 3000 adaptador
            ELSE
                PRINT_STRING_NOW "Fundos insuficientes" 3000
            ENDIF
        ELSE
            PRINT_FORMATTED_NOW "Tanque cheio" 3000
        ENDIF
    ENDIF
ELSE
    GOTO depois_de_tudo
ENDIF
IF volta = 5
    GOTO volta5
ENDIF
IF volta = 6
    GOTO volta6
ENDIF

colocar_combustivel_aviao:
WAIT 28
IF IS_CHAR_SITTING_IN_CAR player carro
    IF GET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel 
        IF combustivel <= 99000 //Para ter uma margem
            IF IS_SCORE_GREATER 0 9 //O cj tem dinheiro? Cada ciclo custa $10
                combustivel += 1000
                adaptador = combustivel
                DIV_INT_LVAR_BY_VAL adaptador 1000   
                SET_EXTENDED_CAR_VAR carro "combustivel" 1 combustivel
                ADD_SCORE 0 -10
                PRINT_FORMATTED_NOW "combustivel em %i/100" 3000 adaptador
            ELSE
                PRINT_STRING_NOW "Fundos insuficientes" 3000
            ENDIF
        ELSE
            PRINT_FORMATTED_NOW "Reservatorio cheio" 3000
        ENDIF
    ENDIF
ELSE
    GOTO depois_de_tudo
ENDIF
IF volta = 8
    GOTO volta8
ENDIF
IF volta = 9
    GOTO volta9
ENDIF

//--------------------------------------------------------------
PRINT_STRING_NOW "Erro de script (mod combustivel)" 3000
GOTO loop_principal //Por segurança, para caso o script "vazar"
//--------------------------------------------------------------

posto_carros:
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 1944.7042, -1773.9180, 15.3737 3.0 
    GOTO colocar_combustivel //ganton
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 1003.8676, -940.0312, 41.1697 3.0
    GOTO colocar_combustivel //temple
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro -94.0802, -1174.8213, 1.9281 3.0
    GOTO colocar_combustivel //caminho para fora
ENDIF

//Las venturas:
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 2211.0422, 2474.3218, 9.8034 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 1596.5 2199.1 10.83 3.0
    GOTO colocar_combustivel
ENDIF    

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 2145.9 2748.16 10.83 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 2642.11 1106.53 10.83 3.0
    GOTO colocar_combustivel
ENDIF    

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 2117.42 920.42 10.83 3.0
    GOTO colocar_combustivel
ENDIF    

//Deserto
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 604.8804 1705.273 6.541 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro -1327.512 2678.107 50.0625 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 68.6979, 1212.8424, 17.8062 3.0
    GOTO colocar_combustivel
ENDIF 

//San fierro
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro -2406.0908, 973.7650, 45.2843 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro -1677.8004, 411.1935, 7.4392 3.0
    GOTO colocar_combustivel
ENDIF    

//Cidade satélite
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro -2244.623 -2561.828 31.9219 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro  652.03 -571.34 16.34 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 1381.68 456.26 19.91 3.0
    GOTO colocar_combustivel
ENDIF

//Outros
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro -1606.1649, -2713.9661, 47.5328 3.0
    GOTO colocar_combustivel
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro  -1471.4800, 1864.3101, 31.6400 3.0
    GOTO colocar_combustivel
ENDIF

IF volta = 5
    GOTO volta5
ENDIF
IF volta = 6
    GOTO volta6
ENDIF

abastecimento_aeronave:
IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro  1576.5782, 1592.9617, 9.7860 10.0
    GOTO colocar_combustivel_aviao //LV
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro  20.3208, 2448.6770, 15.4757 10.0
    GOTO colocar_combustivel_aviao //AB
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro  -1194.4332, -217.9547, 13.1412 10.0
    GOTO colocar_combustivel_aviao //SF
ENDIF

IF LOCATE_CAR_DISTANCE_TO_COORDINATES carro 1938.3087, -2378.5796, 12.5207 10.0
    GOTO colocar_combustivel_aviao //LS
ENDIF
IF volta = 8
    GOTO volta8
ENDIF
IF volta = 9
    GOTO volta9
ENDIF


dar_blips_carros:
//Los santos:
ADD_CLEO_BLIP 55 1944.7042, -1773.9180, TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 1003.8676, -940.0312 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 -94.0802, -1174.8213 TRUE 255 255 255 255 blip
//Las venturas:
ADD_CLEO_BLIP 55 2211.0422, 2474.3218 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 1596.5 2199.1 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 2145.9 2748.16 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 2642.11 1106.53 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 2117.42 920.42 TRUE 255 255 255 255 blip
//Deserto6
ADD_CLEO_BLIP 55 604.8804 1705.273 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 -1327.512 2678.107 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 68.6979, 1212.8424 TRUE 255 255 255 255 blip
//San fierro
ADD_CLEO_BLIP 55 -2414.49 982.081 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 -1679.982 408.9051 TRUE 255 255 255 255 blip
//Cidade satélite
ADD_CLEO_BLIP 55 -2244.623 -2561.828 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 652.03 -571.34 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 1381.68 456.26 TRUE 255 255 255 255 blip
//Outros
ADD_CLEO_BLIP 55 -1606.1649, -2713.9661 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 55 -1471.4800, 1864.3101 TRUE 255 255 255 255 blip
GOTO volta0

dar_blips_avioes:
ADD_CLEO_BLIP 5 1576.5782, 1592.9617 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 5 20.3208, 2448.6770 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 5 -1194.4332, -217.9547 TRUE 255 255 255 255 blip
ADD_CLEO_BLIP 5 1938.3087, -2378.5796 TRUE 255 255 255 255 blip
GOTO volta

configurar_save:
GOSUB apagar_pickups
save = 1
RETURN_SCRIPT_EVENT

apagar_pickups:
//Los santos:
IF GET_PICKUP_THIS_COORD 1944.7042, -1773.9180, 13.3737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD  1003.8676, -940.0312, 42.0668 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD  -94.0802, -1174.8213, 2.3 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 
//Las venturas:
IF GET_PICKUP_THIS_COORD  2211.0422, 2474.3218, 10.8034 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD  1596.5 2199.1 10.83 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD  2145.9 2748.16 10.83 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD  2642.11 1106.53 10.83 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD  2117.4199, 920.4200, 11.3737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

//Deserto6
IF GET_PICKUP_THIS_COORD 604.7341, 1704.5405, 6.8737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD -1327.5120, 2677.6069, 50.3737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD 68.6979, 1212.8424, 18.8112 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

//San fierro
IF GET_PICKUP_THIS_COORD -2406.0908, 973.7650, 45.2843 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD -1677.8004, 411.1935, 7.4392 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

//Cidade satélite
IF GET_PICKUP_THIS_COORD -2244.6414, -2561.8635, 32.8737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD 652.0300, -571.3400, 16.8737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

IF GET_PICKUP_THIS_COORD 1381.6801, 456.2600, 20.3737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4
ENDIF 

//Outros
IF GET_PICKUP_THIS_COORD -1606.1649, -2713.9661, 48.8737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4    
ENDIF 

IF GET_PICKUP_THIS_COORD -1471.4800, 1864.3101, 32.8737 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4  
ENDIF 

//Aviões
IF GET_PICKUP_THIS_COORD 1576.5782, 1592.9617, 10.7860 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4  
ENDIF

IF GET_PICKUP_THIS_COORD 20.8715, 2449.1030, 16.9985 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4  
ENDIF 

IF GET_PICKUP_THIS_COORD -1194.4332, -217.9547, 14.1410 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4  
ENDIF 

IF GET_PICKUP_THIS_COORD 1938.3087, -2378.5796, 14.0207 TRUE pickup
    REMOVE_PICKUP pickup
    GET_PICKUP_POINTER pickup ponteiro
    MAKE_NOP ponteiro 4  
ENDIF 

RETURN

criar_pickups:
GOSUB apagar_pickups

REQUEST_MODEL 1650
REQUEST_MODEL 3632
LOAD_ALL_MODELS_NOW

//Los santos:
CREATE_PICKUP 1650 PICKUP_IN_SHOP 1944.7042, -1773.9180, 13.3737 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 1003.8676, -940.0312, 42.0668 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP -94.0802, -1174.8213, 2.3 pickup
//Las venturas:
CREATE_PICKUP 1650 PICKUP_IN_SHOP 2211.0422, 2474.3218, 10.8034 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 1596.5 2199.1 10.83 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 2145.9 2748.16 10.83 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 2642.11 1106.53 10.83 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 2117.4199, 920.4200, 11.3737 pickup 
//Deserto6
CREATE_PICKUP 1650 PICKUP_IN_SHOP 604.7341, 1704.5405, 6.8737 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP -1327.5120, 2677.6069, 50.3737 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 68.6979, 1212.8424, 18.8112 pickup
//San fierro
CREATE_PICKUP 1650 PICKUP_IN_SHOP -2406.0908, 973.7650, 45.2843 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP -1677.8004, 411.1935, 7.4392 pickup
//Cidade satélite
CREATE_PICKUP 1650 PICKUP_IN_SHOP -2244.6414, -2561.8635, 32.8737 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 652.0300, -571.3400, 16.8737 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP 1381.6801, 456.2600, 20.3737 pickup
//Outros
CREATE_PICKUP 1650 PICKUP_IN_SHOP -1606.1649, -2713.9661, 48.8737 pickup
CREATE_PICKUP 1650 PICKUP_IN_SHOP -1471.4800, 1864.3101, 32.8737 pickup
//Aviões
CREATE_PICKUP 3632 PICKUP_IN_SHOP 1576.5782, 1592.9617, 10.7860 pickup
CREATE_PICKUP 3632 PICKUP_IN_SHOP 20.8715, 2449.1030, 16.9985 pickup
CREATE_PICKUP 3632 PICKUP_IN_SHOP -1194.4332, -217.9547, 14.1410 pickup
CREATE_PICKUP 3632 PICKUP_IN_SHOP 1938.3087, -2378.5796, 14.0207 pickup


MARK_MODEL_AS_NO_LONGER_NEEDED 1650
MARK_MODEL_AS_NO_LONGER_NEEDED 3632

RETURN

}
SCRIPT_END

{
    LVAR_INT cj car resultado

    esta_em_algum_carro_valido:
    resultado = FALSE
    IF IS_CHAR_SITTING_IN_ANY_CAR cj
        GET_CAR_CHAR_IS_USING cj car
        IF DOES_VEHICLE_EXIST car
            resultado = TRUE
        ENDIF
    ENDIF
    CLEO_RETURN 0 resultado
}