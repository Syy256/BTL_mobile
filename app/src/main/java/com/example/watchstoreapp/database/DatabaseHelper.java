package com.example.watchstoreapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "watchstore.db";
    public static final int DB_VERSION = 3;

    // Table names
    public static final String TABLE_HANG = "hang";
    public static final String TABLE_SAN_PHAM = "san_pham";
    public static final String TABLE_NGUOI_DUNG = "nguoi_dung";
    public static final String TABLE_GIO_HANG = "gio_hang";
    public static final String TABLE_HOA_DON = "hoa_don";
    public static final String TABLE_CHI_TIET_HOA_DON = "chi_tiet_hoa_don";

    // hang columns
    public static final String HANG_ID = "id";
    public static final String HANG_TEN = "ten";
    public static final String HANG_XUAT_XU = "xuat_xu";
    public static final String HANG_MO_TA = "mo_ta";
    public static final String HANG_LOGO = "logo";

    // san_pham columns
    public static final String SP_ID = "id";
    public static final String SP_MA = "ma_sp";
    public static final String SP_TEN = "ten";
    public static final String SP_HANG_ID = "hang_id";
    public static final String SP_GIOI_TINH = "gioi_tinh";
    public static final String SP_LOAI_MAY = "loai_may";
    public static final String SP_PHAN_KHUC = "phan_khuc";
    public static final String SP_GIA_BAN = "gia_ban";
    public static final String SP_GIA_KM = "gia_km";
    public static final String SP_NGAY_KT_KM = "ngay_ket_thuc_km";
    public static final String SP_SO_LUONG = "so_luong";
    public static final String SP_ANH = "anh";
    public static final String SP_MO_TA = "mo_ta";

    public static final String SP_CHAT_LIEU_VO = "chat_lieu_vo";
    public static final String SP_CHAT_LIEU_DAY = "chat_lieu_day";
    public static final String SP_KINH = "kinh";
    public static final String SP_BAO_HANH = "bao_hanh";
    public static final String SP_TRANG_THAI = "trang_thai";

    // nguoi_dung columns
    public static final String ND_ID = "id";
    public static final String ND_HO_TEN = "ho_ten";
    public static final String ND_SDT = "sdt";
    public static final String ND_EMAIL = "email";
    public static final String ND_MAT_KHAU = "mat_khau";
    public static final String ND_VAI_TRO = "vai_tro";
    public static final String ND_DIA_CHI = "dia_chi";
    public static final String ND_NGAY_TAO = "ngay_tao";

    // gio_hang columns
    public static final String GH_ID = "id";
    public static final String GH_NGUOI_DUNG_ID = "nguoi_dung_id";
    public static final String GH_SAN_PHAM_ID = "san_pham_id";
    public static final String GH_SO_LUONG = "so_luong";
    public static final String GH_NGAY_THEM = "ngay_them";

    // hoa_don columns
    public static final String HD_ID = "id";
    public static final String HD_MA = "ma_hd";
    public static final String HD_NGUOI_DUNG_ID = "nguoi_dung_id";
    public static final String HD_NGAY_DAT = "ngay_dat";
    public static final String HD_TONG_TIEN = "tong_tien";
    public static final String HD_GIAM_GIA = "giam_gia";
    public static final String HD_THANH_TIEN = "thanh_tien";
    public static final String HD_HINH_THUC_TT = "hinh_thuc_tt";
    public static final String HD_TRANG_THAI = "trang_thai";
    public static final String HD_DIA_CHI_GIAO = "dia_chi_giao";
    public static final String HD_GHI_CHU = "ghi_chu";

    // chi_tiet_hoa_don columns
    public static final String CTHD_ID = "id";
    public static final String CTHD_HOA_DON_ID = "hoa_don_id";
    public static final String CTHD_SAN_PHAM_ID = "san_pham_id";
    public static final String CTHD_SO_LUONG = "so_luong";
    public static final String CTHD_GIA_BAN = "gia_ban";

    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");

        db.execSQL("CREATE TABLE " + TABLE_HANG + " (" +
                HANG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HANG_TEN + " TEXT NOT NULL, " +
                HANG_XUAT_XU + " TEXT, " +
                HANG_MO_TA + " TEXT, " +
                HANG_LOGO + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_SAN_PHAM + " (" +
                SP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SP_MA + " TEXT UNIQUE, " +
                SP_TEN + " TEXT NOT NULL, " +
                SP_HANG_ID + " INTEGER, " +
                SP_GIOI_TINH + " TEXT, " +
                SP_LOAI_MAY + " TEXT, " +
                SP_PHAN_KHUC + " TEXT, " +
                SP_CHAT_LIEU_VO + " TEXT, " +
                SP_CHAT_LIEU_DAY + " TEXT, " +
                SP_KINH + " TEXT, " +
                SP_BAO_HANH + " INTEGER DEFAULT 12, " +
                SP_GIA_BAN + " REAL NOT NULL, " +
                SP_GIA_KM + " REAL DEFAULT 0, " +
                SP_NGAY_KT_KM + " TEXT, " +
                SP_SO_LUONG + " INTEGER DEFAULT 0, " +
                SP_ANH + " TEXT, " +
                SP_MO_TA + " TEXT, " +
                SP_TRANG_THAI + " INTEGER DEFAULT 1, " +
                "FOREIGN KEY (" + SP_HANG_ID + ") REFERENCES " +
                TABLE_HANG + "(" + HANG_ID + "))");

        db.execSQL("CREATE TABLE " + TABLE_NGUOI_DUNG + " (" +
                ND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ND_HO_TEN + " TEXT NOT NULL, " +
                ND_SDT + " TEXT UNIQUE, " +
                ND_EMAIL + " TEXT UNIQUE, " +
                ND_MAT_KHAU + " TEXT NOT NULL, " +
                ND_VAI_TRO + " TEXT DEFAULT 'user', " +
                ND_DIA_CHI + " TEXT, " +
                ND_NGAY_TAO + " TEXT DEFAULT (datetime('now')))");

        db.execSQL("CREATE TABLE " + TABLE_GIO_HANG + " (" +
                GH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GH_NGUOI_DUNG_ID + " INTEGER NOT NULL, " +
                GH_SAN_PHAM_ID + " INTEGER NOT NULL, " +
                GH_SO_LUONG + " INTEGER DEFAULT 1, " +
                GH_NGAY_THEM + " TEXT DEFAULT (datetime('now')), " +
                "UNIQUE (" + GH_NGUOI_DUNG_ID + ", " + GH_SAN_PHAM_ID + "), " +
                "FOREIGN KEY (" + GH_NGUOI_DUNG_ID + ") REFERENCES " + TABLE_NGUOI_DUNG + "(" + ND_ID + "), " +
                "FOREIGN KEY (" + GH_SAN_PHAM_ID + ") REFERENCES " + TABLE_SAN_PHAM + "(" + SP_ID + "))");

        db.execSQL("CREATE TABLE " + TABLE_HOA_DON + " (" +
                HD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HD_MA + " TEXT UNIQUE, " +
                HD_NGUOI_DUNG_ID + " INTEGER NOT NULL, " +
                HD_NGAY_DAT + " TEXT DEFAULT (datetime('now')), " +
                HD_TONG_TIEN + " REAL NOT NULL, " +
                HD_GIAM_GIA + " REAL DEFAULT 0, " +
                HD_THANH_TIEN + " REAL NOT NULL, " +
                HD_HINH_THUC_TT + " TEXT, " +
                HD_TRANG_THAI + " TEXT DEFAULT 'Chờ xác nhận', " +
                HD_DIA_CHI_GIAO + " TEXT, " +
                HD_GHI_CHU + " TEXT, " +
                "FOREIGN KEY (" + HD_NGUOI_DUNG_ID + ") REFERENCES " + TABLE_NGUOI_DUNG + "(" + ND_ID + "))");

        db.execSQL("CREATE TABLE " + TABLE_CHI_TIET_HOA_DON + " (" +
                CTHD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CTHD_HOA_DON_ID + " INTEGER NOT NULL, " +
                CTHD_SAN_PHAM_ID + " INTEGER NOT NULL, " +
                CTHD_SO_LUONG + " INTEGER NOT NULL, " +
                CTHD_GIA_BAN + " REAL NOT NULL, " +
                "FOREIGN KEY (" + CTHD_HOA_DON_ID + ") REFERENCES " + TABLE_HOA_DON + "(" + HD_ID + "), " +
                "FOREIGN KEY (" + CTHD_SAN_PHAM_ID + ") REFERENCES " + TABLE_SAN_PHAM + "(" + SP_ID + "))");

        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHI_TIET_HOA_DON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOA_DON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIO_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOI_DUNG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAN_PHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HANG);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Insert brands
        db.execSQL("INSERT INTO " + TABLE_HANG + " (ten, xuat_xu, mo_ta) VALUES ('Casio', 'Nhật Bản', 'Thương hiệu đồng hồ Nhật nổi tiếng')");
        db.execSQL("INSERT INTO " + TABLE_HANG + " (ten, xuat_xu, mo_ta) VALUES ('Seiko', 'Nhật Bản', 'Đồng hồ chính xác từ Nhật')");
        db.execSQL("INSERT INTO " + TABLE_HANG + " (ten, xuat_xu, mo_ta) VALUES ('Citizen', 'Nhật Bản', 'Công nghệ Eco-Drive tiên tiến')");
        db.execSQL("INSERT INTO " + TABLE_HANG + " (ten, xuat_xu, mo_ta) VALUES ('Daniel Wellington', 'Thụy Điển', 'Phong cách tối giản Scandinavian')");
        db.execSQL("INSERT INTO " + TABLE_HANG + " (ten, xuat_xu, mo_ta) VALUES ('Fossil', 'Mỹ', 'Thiết kế thời trang hiện đại')");

        // Insert products
        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (ma_sp, ten, hang_id, gioi_tinh, loai_may, phan_khuc, chat_lieu_vo, chat_lieu_day, kinh, bao_hanh, gia_ban, gia_km, so_luong, mo_ta, trang_thai) VALUES " +
                "('SP001', 'Casio G-Shock GA-2100', 1, 'Nam', 'Pin', 'Tầm trung', 'Nhựa Carbon', 'Nhựa', 'Mineral', 24, 3500000, 2990000, 15, 'Đồng hồ thể thao chống va đập', 1)");

        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (ma_sp, ten, hang_id, gioi_tinh, loai_may, phan_khuc, chat_lieu_vo, chat_lieu_day, kinh, bao_hanh, gia_ban, gia_km, so_luong, mo_ta, trang_thai) VALUES " +
                "('SP002', 'Casio Edifice EFR-570', 1, 'Nam', 'Pin', 'Tầm trung', 'Thép không gỉ', 'Kim loại', 'Mineral', 24, 4200000, 0, 10, 'Đồng hồ doanh nhân lịch lãm', 1)");

        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (ma_sp, ten, hang_id, gioi_tinh, loai_may, phan_khuc, chat_lieu_vo, chat_lieu_day, kinh, bao_hanh, gia_ban, gia_km, so_luong, mo_ta, trang_thai) VALUES " +
                "('SP003', 'Seiko Presage SPB167', 2, 'Nam', 'Cơ', 'Cao cấp', 'Thép không gỉ', 'Da', 'Sapphire', 36, 12000000, 10500000, 5, 'Đồng hồ cơ tự động cao cấp', 1)");

        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (ma_sp, ten, hang_id, gioi_tinh, loai_may, phan_khuc, chat_lieu_vo, chat_lieu_day, kinh, bao_hanh, gia_ban, gia_km, so_luong, mo_ta, trang_thai) VALUES " +
                "('SP004', 'Daniel Wellington Classic', 4, 'Nữ', 'Pin', 'Tầm trung', 'Thép không gỉ', 'Da', 'Mineral', 12, 2800000, 0, 20, 'Đồng hồ thời trang nữ dây da', 1)");

        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (ma_sp, ten, hang_id, gioi_tinh, loai_may, phan_khuc, chat_lieu_vo, chat_lieu_day, kinh, bao_hanh, gia_ban, gia_km, so_luong, mo_ta, trang_thai) VALUES " +
                "('SP005', 'Fossil Gen 6', 5, 'Unisex', 'Smart', 'Tầm trung', 'Thép không gỉ', 'Silicone', 'Gorilla Glass', 12, 6500000, 5490000, 8, 'Smartwatch thời trang đa năng', 1)");

        db.execSQL("INSERT INTO " + TABLE_SAN_PHAM + " (ma_sp, ten, hang_id, gioi_tinh, loai_may, phan_khuc, chat_lieu_vo, chat_lieu_day, kinh, bao_hanh, gia_ban, gia_km, so_luong, mo_ta, trang_thai) VALUES " +
                "('SP006', 'Citizen Eco-Drive BM7455', 3, 'Nam', 'Solar', 'Tầm trung', 'Thép không gỉ', 'Kim loại', 'Mineral', 24, 5200000, 0, 12, 'Đồng hồ năng lượng mặt trời', 1)");

        // Insert admin account
        db.execSQL("INSERT INTO " + TABLE_NGUOI_DUNG + " (ho_ten, sdt, email, mat_khau, vai_tro, dia_chi) VALUES " +
                "('Admin', '0900000000', 'admin', '123', 'admin', 'TP.HCM')");
        // Insert sample user
        db.execSQL("INSERT INTO " + TABLE_NGUOI_DUNG + " (ho_ten, sdt, email, mat_khau, vai_tro, dia_chi) VALUES " +
                "('Nguyễn Văn A', '0912345678', 'user', '123', 'user', 'Hà Nội')");
    }
}
