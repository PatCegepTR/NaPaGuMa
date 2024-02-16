package com.example.test.ui.Profil;

public class AdapterProfil {
    InterfaceProfil monInterface;

    public AdapterProfil(InterfaceProfil unInterface)
    {
        monInterface = unInterface;
    }

    public class GetProfil
    {
        public void getProfil()
        {
            monInterface.getProfil();
        }
    }


}
