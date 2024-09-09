SCRIPT_START
{
NOP
/*
    Autor: Gabriel Alves (Gabriel432135)
*/

//O número 50 serve para indicar que vai ressetar.

IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 3000 //Enganar o compilador
    GET_PLAYER_CHAR 0 char
    PRINT_STRING_NOW "Erro fatal" 3000
ENDIF

LVAR_INT menu menuB menuC char armaID charID modeloID itemID cpv //economizar variaveis cpv = contador, pedtype e varextended

LVAR_INT (ls arls) (sf arsf) (lv arlv) (rc arrc) (rc2 arrc2) (sw arsw) (fb arfb) (ex arex) (mt armt) 

loop_principal:
IF IS_KEY_PRESSED VK_KEY_P
AND IS_KEY_PRESSED 186 //Ç
    menu_inicial:
    CREATE_MENU NOMEMEN (30.0 30.0) 180.0 1 TRUE TRUE 0 menu
    SET_MENU_COLUMN menu 0 DUMMY (LSTXT SFTXT LVTXT RCTXT RC2TXT SWTXT FBITXT EXTXT MTTXT DESLIGA LIGANDO RESSET)
    SET_PLAYER_CONTROL 0 FALSE
    WAIT 500
    WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
        WAIT 0 
        IF IS_BUTTON_PRESSED PAD1 CROSS //OK
            GET_MENU_ITEM_SELECTED menu itemID
            WAIT 0
            DELETE_MENU menu
            SWITCH itemID
                CASE 0
                    charID = 280 //LS
                    BREAK
                CASE 1
                    charID = 281 //SF 
                    BREAK
                CASE 2
                    charID = 282 //LV 
                    BREAK
                CASE 3
                    charID = 283 //RC
                    BREAK
                CASE 4
                    charID = 288 //RC2
                    BREAK
                CASE 5
                    charID = 285 //SW 
                    BREAK
                CASE 6
                    charID = 286 //FBI
                    BREAK
                CASE 7
                    charID = 287 //EX
                    BREAK
                CASE 8
                    charID = 284 //MT
                    BREAK
                CASE 9
                    GOTO desligar //Desligar mod
                    BREAK
                CASE 10
                    GOTO alterararmas //Religar mod
                    BREAK                                          
                CASE 11
                    GOTO padrao //Para ressetar
                    BREAK        
            ENDSWITCH
            GOTO menu_arma
        ENDIF    
        WAIT 0
    ENDWHILE
    DELETE_MENU menu
    SET_PLAYER_CONTROL 0 TRUE
ENDIF
WAIT 0
GOTO loop_principal


//Segundo menu
menu_arma:
CREATE_MENU NOMEMEB (30.0 30.0) 180.0 1 TRUE TRUE 0 menuB
SET_MENU_COLUMN menuB 0 DUMMY (CAC PISTOLA CL12 SUBM RFASS RIFLES ARMASPE ARREMES ESPECI1 ESPECI2 PRESENT PADRAO)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
    GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuB
        SWITCH itemID
            CASE 0
                GOTO cac
                BREAK
            CASE 1
                GOTO pistola
                BREAK
            CASE 2
                GOTO cl12
                BREAK
            CASE 3
                GOTO subm
                BREAK
            CASE 4
                GOTO rfass
                BREAK
            CASE 5
                GOTO rifles
                BREAK
            CASE 6
                GOTO armaspe
                BREAK
            CASE 7
                GOTO arremes
                BREAK
            CASE 8
                GOTO especi1
                BREAK
            CASE 9
                GOTO especi2
                BREAK
            CASE 10
                GOTO present
                BREAK
            CASE 11
                armaID = 50
                GOTO setarpolicial
                BREAK            
        ENDSWITCH
    ENDIF   
ENDWHILE
DELETE_MENU menuB
GOTO menu_inicial

//menu armas------------------------------
cac:
CREATE_MENU CAC (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA0 ARMA1 ARMA2 ARMA3 ARMA4 ARMA5 ARMA6 ARMA7 ARMA8 ARMA9 PADRAO DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 0 //Mão
                BREAK
            CASE 1
                armaID = 1 //Soco inglês
                BREAK
            CASE 2
                armaID = 2 //Taco de golfe
                BREAK
            CASE 3
                armaID = 4 //Faquinha
                BREAK
            CASE 4
                armaID = 3 //Cassetete
                BREAK
            CASE 5
                armaID = 5 //Baseball
                BREAK
            CASE 6
                armaID = 6 //Pá
                BREAK
            CASE 7
                armaID = 7 //Sinuca
                BREAK
            CASE 8
                armaID = 8 //Katana
                BREAK
            CASE 9
                armaID = 9 //Motosserra
                BREAK
            CASE 10
                armaID = 50 //Resset
                BREAK         
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma


pistola:
CREATE_MENU PISTOLA (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA10 ARMA11 ARMA12 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 22 //Colt 45
                BREAK
            CASE 1
                armaID = 24 //Desert
                BREAK
            CASE 2
                armaID = 23 //Pistola com silenciador
                BREAK
            CASE 3
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

cl12:
CREATE_MENU CL12 (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA13 ARMA14 ARMA15 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 25 //Espingarda
                BREAK
            CASE 1
                armaID = 26 //Desert
                BREAK
            CASE 2
                armaID = 27 //Escopeta de combate
                BREAK
            CASE 3
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

subm: 
CREATE_MENU SUBM (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA16 ARMA17 ARMA18 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 28 //UZI
                BREAK
            CASE 1
                armaID = 29 //MP5
                BREAK
            CASE 2
                armaID = 32 //Tec-9
                BREAK
            CASE 3
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

rfass: 
CREATE_MENU RFASS (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA19 ARMA20 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0    
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 30//AK-47
                BREAK
            CASE 1
                armaID = 31 //M4
                BREAK
            CASE 2
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

rifles: 
CREATE_MENU RIFLES (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA21 ARMA22 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 33 //Rifles
                BREAK
            CASE 1
                armaID = 34 //Sniper
                BREAK
            CASE 2
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

armaspe: 
CREATE_MENU ARMASPE (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA23 ARMA24 ARMA25 ARMA26 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 36 //Bazooka
                BREAK
            CASE 1
                armaID = 35 //RPG
                BREAK
            CASE 2
                armaID = 37 //Lança chamas
                BREAK
            CASE 3
                armaID = 38 //Minigun
                BREAK
            CASE 4
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

arremes: 
CREATE_MENU ARREMES (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA27 ARMA28 ARMA29 ARMA30 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 16 //Granada
                BREAK
            CASE 1
                armaID = 17 //Lacrimogeneo
                BREAK
            CASE 2
                armaID = 18 //Molotov
                BREAK
            CASE 3
                armaID = 39 //Cargas Explosivas
                BREAK
            CASE 4
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

especi1: 
CREATE_MENU ESPECI1 (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA31 ARMA32 ARMA33 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 41 //Spray
                BREAK
            CASE 1
                armaID = 42 //Extintor
                BREAK
            CASE 2
                armaID = 43 //Câmera
                BREAK
            CASE 3
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

especi2: 
CREATE_MENU ESPECI2 (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA34 ARMA35 ARMA36 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 44 //Visão Noturna
                BREAK
            CASE 1
                armaID = 45 //Infra vermelho
                BREAK
            CASE 2
                armaID = 46 //Pára-quedas
                BREAK
            CASE 3
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

present: 
CREATE_MENU PRESENT (30.0 30.0) 180.0 1 TRUE TRUE 0 menuC
SET_MENU_COLUMN menuB 0 DUMMY (ARMA37 ARMA38 ARMA39 ARMA40 ARMA41 ARMA42 PADRAO DUMMY DUMMY DUMMY DUMMY DUMMY)
WAIT 500
WHILE NOT IS_BUTTON_PRESSED PAD1 TRIANGLE //VOLTAR/SAIR
    WAIT 0 
    IF IS_BUTTON_PRESSED PAD1 CROSS //OK
        GET_MENU_ITEM_SELECTED menu itemID
        WAIT 0
        DELETE_MENU menuC
        SWITCH itemID
            CASE 0
                armaID = 10 //Dildo roxo
                BREAK
            CASE 1
                armaID = 13 //Dildo
                BREAK
            CASE 2
                armaID = 11 //Vibrador
                BREAK
            CASE 3
                armaID = 14 //Flor
                BREAK
            CASE 4
                armaID = 12 //Vibrador longo
                BREAK
            CASE 5
                armaID = 15 //Bengala
                BREAK            
            CASE 6
                armaID = 50 //Arma padrão
                BREAK
        ENDSWITCH
        GOTO setarpolicial
    ENDIF   
ENDWHILE
DELETE_MENU menuC
GOTO menu_arma

setarpolicial:
WAIT 0
SWITCH charID
    CASE 280
        ls = 1
        arls = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            ls = 0
        ENDIF
        BREAK
    CASE 281
        sf = 1
        arsf = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            sf = 0
        ENDIF
        BREAK
    CASE 282
        lv = 1
        arlv = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            lv = 0
        ENDIF
        BREAK
    CASE 283
        rc = 1
        arrc = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            rc = 0
        ENDIF
        BREAK
    CASE 284
        mt = 1
        armt = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            mt = 0
        ENDIF
        BREAK
    CASE 285
        sw = 1
        arsw = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            sw = 0
        ENDIF
        BREAK
    CASE 286
        fb = 1
        arfb = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            fb = 0
        ENDIF
        BREAK
    CASE 287
        ex = 1
        arex = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            ex = 0
        ENDIF
        BREAK
    CASE 288
        rc2 = 1
        arrc2 = armaID
        IF IS_INT_LVAR_EQUAL_TO_CONSTANT armaID 50
            rc2 = 0
        ENDIF
        BREAK                                                                        
ENDSWITCH

//Limpando memória
cpv = 330
WHILE cpv < 373
    IF IS_MODEL_AVAILABLE cpv
        MARK_MODEL_AS_NO_LONGER_NEEDED cpv
    ENDIF
    cpv ++
ENDWHILE

//Carregando armas
IF ls=1
AND NOT arls=0//mão
    GET_WEAPONTYPE_MODEL arls cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma de LS" 100
        WAIT 0
    ENDWHILE
ENDIF
IF sf=1
AND NOT arsf=0//mão
    GET_WEAPONTYPE_MODEL arsf cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma de SF" 100
        WAIT 0
    ENDWHILE
ENDIF
IF lv=1
AND NOT arlv=0//mão
    GET_WEAPONTYPE_MODEL arlv cpv
    REQUEST_MODEL  cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma de LV" 100
        WAIT 0
    ENDWHILE
ENDIF
IF rc=1
AND NOT arrc=0//mão
    GET_WEAPONTYPE_MODEL arrc cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma da P.C" 100
        WAIT 0
    ENDWHILE
ENDIF
IF mt=1
AND NOT armt=0//mão
    GET_WEAPONTYPE_MODEL armt cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma de Batedor" 100
        WAIT 0
    ENDWHILE
ENDIF
IF sw=1
AND NOT arsw=0//mão
    GET_WEAPONTYPE_MODEL arsw cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma da SWAT" 100
        WAIT 0
    ENDWHILE
ENDIF
IF fb=1
AND NOT arfb=0//mão
    GET_WEAPONTYPE_MODEL arfb cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma de FBI" 100
        WAIT 0
    ENDWHILE
ENDIF
IF ex=1
AND NOT arex=0//mão
    GET_WEAPONTYPE_MODEL arex cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma do exército" 100
        WAIT 0
    ENDWHILE
ENDIF
IF rc2=1
AND NOT arrc2=0//mão
    GET_WEAPONTYPE_MODEL arrc2 cpv
    REQUEST_MODEL cpv
    WHILE NOT HAS_MODEL_LOADED cpv
        PRINT_STRING_NOW "Carregando arma da P.C 2" 100
        WAIT 0
    ENDWHILE
ENDIF
WAIT 0
GOTO alterararmas


padrao: 
ls=0
arls=50
sf=0
arsf=50
lv=0
arlv=50
rc=0
arrc=50
rc2=0
arrc2=50
sw=0
arsw=50
fb=0
arfb=50
ex=0
arex=50
mt=0
armt=50
WAIT 0
SET_PLAYER_CONTROL 0 TRUE
GOTO loop_principal


alterararmas:
SET_SCRIPT_EVENT_CHAR_CREATE ON alteracao char
SET_SCRIPT_EVENT_CHAR_PROCESS ON alteracaoprocess char
SET_PLAYER_CONTROL 0 TRUE
GOTO loop_principal

alteracao:
GET_CHAR_MODEL char modeloID
GET_PED_TYPE char cpv
IF cpv = 6 //É policial
    SWITCH modeloID
        CASE 280
            IF ls = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arls 300000
                SET_CURRENT_CHAR_WEAPON char arls
            ENDIF
            BREAK
        CASE 281
            IF sf = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arsf 300000
                SET_CURRENT_CHAR_WEAPON char arsf
            ENDIF
            BREAK       
        CASE 282
            IF lv = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arlv 300000
                SET_CURRENT_CHAR_WEAPON char arlv
            ENDIF
            BREAK        
        CASE 283
            IF rc = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arrc 300000
                SET_CURRENT_CHAR_WEAPON char arrc
            ENDIF
            BREAK       
        CASE 284
            IF mt = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char armt 300000
                SET_CURRENT_CHAR_WEAPON char armt
            ENDIF
            BREAK        
        CASE 285
            IF sw = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arsw 300000
                SET_CURRENT_CHAR_WEAPON char arsw
            ENDIF 
            BREAK   
        CASE 286
            IF fb = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arfb 300000
                SET_CURRENT_CHAR_WEAPON char arfb
            ENDIF
            BREAK      
        CASE 287
            IF ex = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arex 300000
                SET_CURRENT_CHAR_WEAPON char arex
            ENDIF
            BREAK       
        CASE 288
            IF rc2 = 1
                REMOVE_ALL_CHAR_WEAPONS char
                GIVE_WEAPON_TO_CHAR char arrc2 300000
                SET_CURRENT_CHAR_WEAPON char arrc2
            ENDIF
            BREAK      
    ENDSWITCH
    //marcar para saber que ele já passou pelo script. Para uma futura atualização.
    IF NOT GET_EXTENDED_CHAR_VAR char "ap" 1 cpv
        INIT_EXTENDED_CHAR_VARS char "ap" 1
        SET_EXTENDED_CHAR_VAR char "ap" 1 1   
    ENDIF
ENDIF
RETURN_SCRIPT_EVENT


alteracaoprocess:
//verificar
IF GET_EXTENDED_CHAR_VAR char "ap" 1 cpv
    SWITCH modeloID
        CASE 280
            IF ls = 1
                SET_CURRENT_CHAR_WEAPON char arls
            ENDIF
            BREAK
        CASE 281
            IF sf = 1
                SET_CURRENT_CHAR_WEAPON char arsf
            ENDIF
            BREAK       
        CASE 282
            IF lv = 1
                SET_CURRENT_CHAR_WEAPON char arlv
            ENDIF
            BREAK        
        CASE 283
            IF rc = 1
                SET_CURRENT_CHAR_WEAPON char arrc
            ENDIF
            BREAK       
        CASE 284
            IF mt = 1
                SET_CURRENT_CHAR_WEAPON char armt
            ENDIF
            BREAK        
        CASE 285
            IF sw = 1
                SET_CURRENT_CHAR_WEAPON char arsw
            ENDIF
            BREAK   
        CASE 286
            IF fb = 1
                SET_CURRENT_CHAR_WEAPON char arfb
            ENDIF
            BREAK      
        CASE 287
            IF ex = 1
                SET_CURRENT_CHAR_WEAPON char arex
            ENDIF
            BREAK       
        CASE 288
            IF rc2 = 1
                SET_CURRENT_CHAR_WEAPON char arrc2
            ENDIF
            BREAK      
    ENDSWITCH
ENDIF
RETURN_SCRIPT_EVENT


desligar:
SET_SCRIPT_EVENT_CHAR_CREATE OFF alteracao char
SET_SCRIPT_EVENT_CHAR_PROCESS OFF alteracaoprocess char
SET_PLAYER_CONTROL 0 TRUE
GOTO loop_principal

nada:
NOP
RETURN_SCRIPT_EVENT

}
SCRIPT_END