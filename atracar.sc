/*
    Autor: Gabriel Alves (Gabriel432135)
*/
SCRIPT_START
{
LVAR_INT portador player carrochar
LVAR_FLOAT xc yc zc
GET_PLAYER_CHAR 0 player    
loop:
IF IS_KEY_PRESSED VK_KEY_G
   IF IS_CHAR_IN_ANY_CAR player

      IF IS_PLAYER_IN_REMOTE_MODE 0 //compatibilidade com Rc cars
        GET_REMOTE_CONTROLLED_CAR 0 carrochar
      ELSE  
        GET_CAR_CHAR_IS_USING player carrochar
      ENDIF//----------------------------------------------------------

      GET_CAR_COORDINATES carrochar xc yc zc

      IF IS_VEHICLE_ATTACHED carrochar
        DETACH_CAR carrochar 0.0 0.0 0.0 TRUE
        WAIT 1000
        PRINT_STRING_NOW "O VEICULO FOI LIBERADO" 1000
        GOTO loop
      ENDIF  

      IF GET_RANDOM_CAR_IN_SPHERE_NO_SAVE_RECURSIVE xc yc zc 60.0 TRUE TRUE portador
        //Verificações
        //-------------------------------------------------------------------------------------------------------------------------------
        IF portador=carrochar //Para evitar que o carro atraque nele mesmo
            GOTO loop
        ENDIF

        IF IS_VEHICLE_ATTACHED portador //Veículos não podem atracar em veículos já atracados
            GOTO loop
        ENDIF

        IF NOT IS_CAR_TOUCHING_CAR portador carrochar //Para o carro não atracar num veículo que não está encostando. Fica estranho kkk
            GOTO loop
        ENDIF
        //-------------------------------------------------------------------------------------------------------------------------------
        ATTACH_CAR_TO_CAR carrochar portador -1000.0 0.0 0.0 0.0 0.0 0.0
        PRINT_STRING_NOW "O VEICULO FOI FIXADO" 1000
        WAIT 1000
        //-----------------------------------------------
      ENDIF 
   ENDIF
ENDIF
WAIT 0
GOTO loop
}
SCRIPT_END