package com.tubes2_btc.Classes;

import java.util.List;
import java.util.ArrayList;

public class CardConstants {
    // Card index
    public static final int CARD_EMPTY_INDEX = 0;

    public static final int CARD_HIU_INDEX = 1;
    public static final int CARD_SAPI_INDEX = 2;
    public static final int CARD_DOMBA_INDEX = 3;
    public static final int CARD_KUDA_INDEX = 4;
    public static final int CARD_AYAM_INDEX = 5;
    public static final int CARD_BERUANG_INDEX = 6;

    public static final int CARD_LABU_INDEX = 7;
    public static final int CARD_JAGUNG_INDEX = 8;
    public static final int CARD_STROBERI_INDEX = 9;
    public static final int CARD_SUSU_INDEX = 10;
    public static final int CARD_TELUR_INDEX = 11;
    public static final int CARD_SIRIP_HIU_INDEX = 12;
    public static final int CARD_DAGING_KUDA_INDEX = 13;
    public static final int CARD_DAGING_DOMBA_INDEX = 14;
    public static final int CARD_DAGING_BERUANG_INDEX = 15;

    public static final int CARD_BIJI_LABU_INDEX = 16;
    public static final int CARD_BIJI_JAGUNG_INDEX = 17;
    public static final int CARD_BIJI_STROBERI_INDEX = 18;

    public static final int CARD_ACCELERATE_INDEX = 19;
    public static final int CARD_DELAY_INDEX = 20;
    public static final int CARD_INSTANT_HARVEST_INDEX = 21;
    public static final int CARD_DESTROY_INDEX = 22;
    public static final int CARD_PROTECT_INDEX = 23;
    public static final int CARD_TRAP_INDEX = 24;

    // Card names
    public static final String CARD_EMPTY = "";

    public static final String CARD_HIU = "Hiu Darat";
    public static final String CARD_SAPI = "Sapi";
    public static final String CARD_DOMBA = "Domba";
    public static final String CARD_KUDA = "Kuda";
    public static final String CARD_AYAM = "Ayam";
    public static final String CARD_BERUANG = "Beruang";

    public static final String CARD_LABU = "Labu";
    public static final String CARD_JAGUNG = "Jagung";
    public static final String CARD_STROBERI = "Stroberi";
    public static final String CARD_SUSU = "Susu";
    public static final String CARD_TELUR = "Telur";
    public static final String CARD_SIRIP_HIU = "Sirip Hiu";
    public static final String CARD_DAGING_KUDA = "Daging Kuda";
    public static final String CARD_DAGING_DOMBA = "Daging Domba";
    public static final String CARD_DAGING_BERUANG = "Daging Beruang";

    public static final String CARD_BIJI_LABU = "Biji Labu";
    public static final String CARD_BIJI_JAGUNG = "Biji Jagung";
    public static final String CARD_BIJI_STROBERI = "Biji Stroberi";

    public static final String CARD_ACCELERATE = "Accelerate";
    public static final String CARD_DELAY = "Delay";
    public static final String CARD_INSTANT_HARVEST = "Instant Harvest";
    public static final String CARD_DESTROY = "Destroy";
    public static final String CARD_PROTECT = "Protect";
    public static final String CARD_TRAP = "Trap";

    // Card image paths
    public static final String CARD_HIU_PATH = "/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png";
    public static final String CARD_SAPI_PATH = "/com/tubes2_btc/Pages/Images/Hewan/cow.png";
    public static final String CARD_DOMBA_PATH = "/com/tubes2_btc/Pages/Images/Hewan/sheep.png";
    public static final String CARD_KUDA_PATH = "/com/tubes2_btc/Pages/Images/Hewan/horse.png";
    public static final String CARD_AYAM_PATH = "/com/tubes2_btc/Pages/Images/Hewan/chicken.png";
    public static final String CARD_BERUANG_PATH = "/com/tubes2_btc/Pages/Images/Hewan/bear.png";

    public static final String CARD_BIJI_LABU_PATH = "/com/tubes2_btc/Pages/Images/Tanaman/pumpkin seeds.png";
    public static final String CARD_BIJI_JAGUNG_PATH = "/com/tubes2_btc/Pages/Images/Tanaman/corn seeds.png";
    public static final String CARD_BIJI_STROBERI_PATH = "/com/tubes2_btc/Pages/Images/Tanaman/strawberry seeds.png";

    // pumpkin
    public static final String CARD_LABU_PATH = "/com/tubes2_btc/Pages/Images/Produk/pumpkin.png";

    // corn
    public static final String CARD_JAGUNG_PATH = "/com/tubes2_btc/Pages/Images/Produk/corn.png";

    // strawberry
    public static final String CARD_STROBERI_PATH = "/com/tubes2_btc/Pages/Images/Produk/strawberry.png";

    // susu
    public static final String CARD_SUSU_PATH = "/com/tubes2_btc/Pages/Images/Produk/susu.png";

    // telur
    public static final String CARD_TELUR_PATH = "/com/tubes2_btc/Pages/Images/Produk/telur.png";

    // shark-fin
    public static final String CARD_SIRIP_HIU_PATH = "/com/tubes2_btc/Pages/Images/Produk/shark-fin.png";

    // Daging Kuda
    public static final String CARD_DAGING_KUDA_PATH = "/com/tubes2_btc/Pages/Images/Produk/Daging Kuda.png";

    // Daging Domba
    public static final String CARD_DAGING_DOMBA_PATH = "/com/tubes2_btc/Pages/Images/Produk/Daging Domba.png";

    // Daging Beruang
    public static final String CARD_DAGING_BERUANG_PATH = "/com/tubes2_btc/Pages/Images/Produk/Daging Beruang.png";

    // Accelerate
    public static final String CARD_ACCELERATE_PATH = "/com/tubes2_btc/Pages/Images/Item/Accelerate.png";

    // Delay
    public static final String CARD_DELAY_PATH = "/com/tubes2_btc/Pages/Images/Item/Delay.png";

    // Instant Harvest
    public static final String CARD_INSTANT_HARVEST_PATH = "/com/tubes2_btc/Pages/Images/Item/Instant Harvest.png";

    // Destroy
    public static final String CARD_DESTROY_PATH = "/com/tubes2_btc/Pages/Images/Item/Destroy.png";

    // Protect
    public static final String CARD_PROTECT_PATH = "/com/tubes2_btc/Pages/Images/Item/Protect.png";

    // Trap
    public static final String CARD_TRAP_PATH = "/com/tubes2_btc/Pages/Images/Item/bear trap.png";

    public static Card createCard(int i) {
        switch (i) {
            case CARD_EMPTY_INDEX:
                return new Card(CARD_EMPTY, "");

            case CARD_HIU_INDEX:
                return new Animal(CARD_HIU, CARD_HIU_PATH, 0, 20, Animal.ANIMAL_KARNIVORA, CARD_SIRIP_HIU);
            case CARD_SAPI_INDEX:
                return new Animal(CARD_SAPI, CARD_SAPI_PATH, 0, 10, Animal.ANIMAL_HERBIVORA, CARD_SUSU);
            case CARD_DOMBA_INDEX:
                return new Animal(CARD_DOMBA, CARD_DOMBA_PATH, 0, 12, Animal.ANIMAL_HERBIVORA, CARD_DAGING_DOMBA);
            case CARD_KUDA_INDEX:
                return new Animal(CARD_KUDA, CARD_KUDA_PATH, 0, 14, Animal.ANIMAL_HERBIVORA, CARD_DAGING_KUDA);
            case CARD_AYAM_INDEX:
                return new Animal(CARD_AYAM, CARD_AYAM_PATH, 0, 5, Animal.ANIMAL_OMNIVORA, CARD_TELUR);
            case CARD_BERUANG_INDEX:
                return new Animal(CARD_BERUANG, CARD_BERUANG_PATH, 0, 25, Animal.ANIMAL_OMNIVORA, CARD_DAGING_BERUANG);

            case CARD_BIJI_LABU_INDEX:
                return new Plant(CARD_BIJI_LABU, CARD_BIJI_LABU_PATH, 0, 5, CARD_LABU);
            case CARD_BIJI_JAGUNG_INDEX:
                return new Plant(CARD_BIJI_JAGUNG, CARD_BIJI_JAGUNG_PATH, 0, 3, CARD_JAGUNG);
            case CARD_BIJI_STROBERI_INDEX:
                return new Plant(CARD_BIJI_STROBERI, CARD_BIJI_STROBERI_PATH, 0, 4, CARD_STROBERI);

            case CARD_SIRIP_HIU_INDEX:
                return new Product(CARD_SIRIP_HIU, CARD_SIRIP_HIU_PATH, 500, 12, Product.PRODUCT_CARNIVORE_FOOD);
            case CARD_SUSU_INDEX:
                return new Product(CARD_SUSU, CARD_SUSU_PATH, 100, 4, Product.PRODUCT_CARNIVORE_FOOD);
            case CARD_DAGING_DOMBA_INDEX:
                return new Product(CARD_DAGING_DOMBA, CARD_DAGING_DOMBA_PATH, 120, 6, Product.PRODUCT_CARNIVORE_FOOD);
            case CARD_DAGING_KUDA_INDEX:
                return new Product(CARD_DAGING_KUDA, CARD_DAGING_KUDA_PATH, 150, 8, Product.PRODUCT_CARNIVORE_FOOD);
            case CARD_TELUR_INDEX:
                return new Product(CARD_TELUR, CARD_TELUR_PATH, 50, 2, Product.PRODUCT_CARNIVORE_FOOD);
            case CARD_DAGING_BERUANG_INDEX:
                return new Product(CARD_DAGING_BERUANG, CARD_DAGING_BERUANG_PATH, 500, 12, Product.PRODUCT_CARNIVORE_FOOD);
            case CARD_JAGUNG_INDEX:
                return new Product(CARD_JAGUNG, CARD_JAGUNG_PATH, 150, 3, Product.PRODUCT_HERBIVORE_FOOD);
            case CARD_LABU_INDEX:
                return new Product(CARD_LABU, CARD_LABU_PATH, 500, 10, Product.PRODUCT_HERBIVORE_FOOD);
            case CARD_STROBERI_INDEX:
                return new Product(CARD_STROBERI, CARD_STROBERI_PATH, 350, 5, Product.PRODUCT_HERBIVORE_FOOD);

            case CARD_ACCELERATE_INDEX:
                return new Card(CARD_ACCELERATE, CARD_ACCELERATE_PATH);
            case CARD_DELAY_INDEX:
                return new Card(CARD_DELAY, CARD_DELAY_PATH);
            case CARD_INSTANT_HARVEST_INDEX:
                return new Card(CARD_INSTANT_HARVEST, CARD_INSTANT_HARVEST_PATH);
            case CARD_DESTROY_INDEX:
                return new Card(CARD_DESTROY, CARD_DESTROY_PATH);
            case CARD_PROTECT_INDEX:
                return new Card(CARD_PROTECT, CARD_PROTECT_PATH);
            case CARD_TRAP_INDEX:
                return new Card(CARD_TRAP, CARD_TRAP_PATH);

            default:
                return new Card(CARD_EMPTY, "");
        }
    }
}
