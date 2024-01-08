package com.binance.connector.myyyyyFUTURE.MYTEST.syshnostytest;

public class TestSvechaDDTO {

    double cenaVerhaTela;
    double cenzVerhaHvosta;

    double raznicaVceneTrend;

    double raznicaVprocentah;

    double raznicaHvostaVCene;


    double hvostBolsheVRaz;

    public TestSvechaDDTO(double cenaVerhaTela, double cenzVerhaHvosta, double raznicaVceneTrend, double raznicaVprocentah, double raznicaHvostaVCene, double hvostBolsheVRaz) {
        this.cenaVerhaTela = cenaVerhaTela;
        this.cenzVerhaHvosta = cenzVerhaHvosta;
        this.raznicaVceneTrend = raznicaVceneTrend;
        this.raznicaVprocentah = raznicaVprocentah;
        this.raznicaHvostaVCene = raznicaHvostaVCene;
        this.hvostBolsheVRaz = hvostBolsheVRaz;
    }

    @Override
    public String toString() {
        return "TestSvechaDDTO{" +
                "cenaVerhaTela=" + cenaVerhaTela +
                ", cenzVerhaHvosta=" + cenzVerhaHvosta +
                ", raznicaVceneTrend=" + raznicaVceneTrend +
                ", raznicaVprocentah=" + raznicaVprocentah +
//                ", raznicaHvostaVCene=" + raznicaHvostaVCene +
                ", raznicaHvostaVCene=" + String.format("%.4f", raznicaHvostaVCene) +
                ", hvostBolsheVRaz=" + hvostBolsheVRaz +
                '}';
    }
}
