# WatchStoreApp

Ứng dụng Android để mua bán đồng hồ — khách hàng mua sắm, admin quản lý. Dữ liệu lưu local bằng SQLite, không cần Internet.

---

## Giới thiệu

Xuất phát từ thực tế là nhiều cửa hàng đồng hồ nhỏ lẻ vẫn chưa có kênh bán hàng trên di động, nhóm xây dựng ứng dụng này để giải quyết hai bài toán cùng lúc: khách hàng cần chỗ để xem và đặt hàng, còn chủ cửa hàng cần quản lý kho và đơn hàng ở một nơi tập trung.

Ứng dụng chạy hoàn toàn offline, mọi dữ liệu được lưu trực tiếp trên thiết bị.

**Stack:** Java · Android SDK (minSdk 24, targetSdk 34) · SQLite

---

## Tính năng

**Phía khách hàng**
- Đăng ký, đăng nhập, đăng xuất
- Duyệt danh sách đồng hồ, lọc theo hãng
- Xem chi tiết sản phẩm (ảnh, mô tả, giá)
- Thêm vào giỏ hàng, chỉnh số lượng, đặt hàng
- Xem lịch sử đơn hàng và chi tiết từng đơn
- Chỉnh sửa hồ sơ cá nhân

**Phía admin**
- Dashboard tổng quan
- Quản lý hãng đồng hồ (thêm / sửa / xóa)
- Quản lý sản phẩm kèm ảnh
- Xem và cập nhật trạng thái đơn hàng
- Xem danh sách người dùng

---

## Cơ sở dữ liệu

Database SQLite được khởi tạo qua `DatabaseHelper.java` với 6 bảng:

| Bảng | Mô tả |
|---|---|
| `NguoiDung` | Người dùng — cả khách lẫn admin (`id`, `tenDangNhap`, `matKhau`, `hoTen`, `email`, `soDienThoai`, `vaiTro`) |
| `Hang` | Hãng đồng hồ (`id`, `tenHang`, `anhHang`) |
| `SanPham` | Sản phẩm (`id`, `tenSanPham`, `moTa`, `gia`, `soLuong`, `anhSanPham`, `idHang`) |
| `GioHang` | Giỏ hàng (`id`, `idNguoiDung`, `idSanPham`, `soLuong`) |
| `HoaDon` | Đơn hàng (`id`, `idNguoiDung`, `ngayDat`, `tongTien`, `trangThai`) |
| `ChiTietHoaDon` | Chi tiết đơn (`id`, `idHoaDon`, `idSanPham`, `soLuong`, `donGia`) |

Lần chạy đầu tiên, app tự seed sẵn một số dữ liệu mẫu: tài khoản admin mặc định, các hãng (Casio, Seiko, Citizen, Fossil, DW) và một số sản phẩm với ảnh drawable đi kèm.

---

## Kiến trúc

```
Activity / UI
    ↓
DAO Layer (CRUD)
    ↓
DatabaseHelper (SQLiteOpenHelper)
    ↓
SQLite trên thiết bị
```

Luồng điều hướng:

```
SplashActivity
    ├── Chưa đăng nhập → LoginActivity / RegisterActivity
    └── Đã đăng nhập
            ├── USER → MainActivity
            │       ├── Danh sách & chi tiết sản phẩm
            │       ├── Giỏ hàng → Đặt hàng
            │       ├── Lịch sử đơn hàng
            │       └── Hồ sơ cá nhân
            └── ADMIN → AdminDashboardActivity
                    ├── Quản lý hãng
                    ├── Quản lý sản phẩm
                    ├── Quản lý đơn hàng
                    └── Quản lý người dùng
```

Phiên đăng nhập được lưu qua `SessionManager` (SharedPreferences). Format tiền và ngày tháng dùng chung qua `FormatUtils`.

---

## Hướng dẫn chạy

**Yêu cầu:**
- Android Studio Hedgehog (2023.1.1) trở lên
- JDK 17+
- Android SDK API 24+
- Thiết bị hoặc emulator Android 7.0+

**Các bước:**

```bash
# 1. Clone về máy
git clone https://github.com/Syy256/BTL_mobile.git

# 2. Mở trong Android Studio
# File → Open → chọn thư mục WatchStoreApp/

# 3. Đợi Gradle sync xong (lần đầu cần Internet để tải dependencies)

# 4. Tạo emulator hoặc cắm thiết bị thật, rồi nhấn Run (Shift+F10)
```

Nếu muốn cài APK trực tiếp không qua Android Studio:

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Tài khoản mặc định:**

| Vai trò | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| User | Tự đăng ký | — |

---

## Cấu trúc thư mục

```
WatchStoreApp/
├── app/src/main/
│   ├── java/com/example/watchstoreapp/
│   │   ├── activity/       # Các màn hình UI
│   │   ├── adapter/        # Adapter cho RecyclerView/ListView
│   │   ├── dao/            # Thao tác CRUD với từng bảng
│   │   ├── database/       # DatabaseHelper.java
│   │   ├── model/          # POJO tương ứng với các bảng
│   │   └── utils/          # FormatUtils, SessionManager
│   └── res/
│       ├── drawable/       # Ảnh sản phẩm
│       ├── layout/         # XML giao diện
│       ├── menu/
│       └── values/
├── data/                   # Ghi chú về seed data
├── demo/                   # Hướng dẫn cài APK nhanh
├── reports/                # Báo cáo đồ án (.pdf)
└── slides/                 # Slide thuyết trình (.pptx)
```

---

## Sinh viên

| Họ và tên | MSSV |
|---|---|
| Đặng Văn Sỹ | 12523077 |
| Đồng Huy Tài | 12523078 |
