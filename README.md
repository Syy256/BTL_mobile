
> Ứng dụng di động Android cho phép người dùng mua sắm đồng hồ trực tuyến, quản lý giỏ hàng, theo dõi đơn hàng và quản trị hệ thống bán hàng.

---

## 📋 Mục Lục

1. [Giới thiệu đề tài](#1-giới-thiệu-đề-tài)
2. [Cơ sở dữ liệu](#2-cơ-sở-dữ-liệu)
3. [Kiến trúc & Pipeline](#3-kiến-trúc--pipeline)
4. [Công nghệ sử dụng](#4-công-nghệ-sử-dụng)
5. [Tính năng chính](#5-tính-năng-chính)
6. [Hướng dẫn chạy](#6-hướng-dẫn-chạy)
7. [Cấu trúc thư mục](#7-cấu-trúc-thư-mục)
8. [Tác giả](#8-tác-giả)

---

## 1. Giới Thiệu Đề Tài

### Bài toán
Hiện nay, việc mua sắm đồng hồ trực tuyến ngày càng phổ biến. Tuy nhiên, nhiều cửa hàng nhỏ lẻ vẫn chưa có nền tảng di động để tiếp cận khách hàng hiệu quả. Người quản lý cũng cần công cụ để quản lý kho hàng, đơn hàng và người dùng một cách tập trung.

### Mục tiêu
- Xây dựng ứng dụng Android cho phép khách hàng duyệt sản phẩm, thêm vào giỏ hàng và đặt hàng.
- Cung cấp giao diện quản trị (Admin) để quản lý sản phẩm, hãng đồng hồ, đơn hàng và người dùng.
- Lưu trữ dữ liệu cục bộ bằng SQLite, không yêu cầu kết nối Internet.

### Phạm vi
- Nền tảng: Android (minSdk 24, targetSdk 34)
- Ngôn ngữ: Java
- CSDL: SQLite (Room / SQLiteOpenHelper)

---

## 2. Cơ Sở Dữ Liệu

Ứng dụng sử dụng **SQLite** lưu trữ cục bộ trên thiết bị. Database được khởi tạo qua `DatabaseHelper.java`.

### Các bảng dữ liệu

| Bảng | Mô tả | Các cột chính |
|---|---|---|
| `NguoiDung` | Người dùng (khách & admin) | `id`, `tenDangNhap`, `matKhau`, `hoTen`, `email`, `soDienThoai`, `vaiTro` |
| `Hang` | Hãng đồng hồ | `id`, `tenHang`, `anhHang` |
| `SanPham` | Sản phẩm đồng hồ | `id`, `tenSanPham`, `moTa`, `gia`, `soLuong`, `anhSanPham`, `idHang` |
| `GioHang` | Giỏ hàng | `id`, `idNguoiDung`, `idSanPham`, `soLuong` |
| `HoaDon` | Đơn hàng | `id`, `idNguoiDung`, `ngayDat`, `tongTien`, `trangThai` |
| `ChiTietHoaDon` | Chi tiết đơn hàng | `id`, `idHoaDon`, `idSanPham`, `soLuong`, `donGia` |

### Dữ liệu mẫu
Ứng dụng tự động seed dữ liệu khi khởi chạy lần đầu, bao gồm:
- Tài khoản admin mặc định: `admin` / `admin123`
- Các hãng đồng hồ: Casio, Seiko, Citizen, Fossil, DW
- Sản phẩm mẫu với hình ảnh có sẵn trong `res/drawable`

---

## 3. Kiến Trúc & Pipeline

### Kiến trúc ứng dụng

```
Người dùng
    │
    ▼
[Activity / UI Layer]
    │  (gọi DAO)
    ▼
[DAO Layer – Data Access Objects]
    │  (truy vấn SQL)
    ▼
[DatabaseHelper – SQLiteOpenHelper]
    │
    ▼
[SQLite Database trên thiết bị]
```

### Luồng chính

```
Khởi động → SplashActivity
    │
    ├── Chưa đăng nhập → LoginActivity / RegisterActivity
    │       └── Xác thực → SessionManager (SharedPreferences)
    │
    └── Đã đăng nhập
            ├── Role = USER → MainActivity
            │       ├── Danh sách sản phẩm (SanPhamAdapter)
            │       ├── Chi tiết sản phẩm → ProductDetailActivity
            │       ├── Giỏ hàng → CartActivity → Đặt hàng
            │       ├── Lịch sử đơn hàng → OrderHistoryActivity → OrderDetailActivity
            │       └── Hồ sơ cá nhân → ProfileActivity
            │
            └── Role = ADMIN → AdminDashboardActivity
                    ├── Quản lý Hãng → AdminBrandActivity / AdminBrandFormActivity
                    ├── Quản lý Sản phẩm → AdminProductActivity / AdminProductFormActivity
                    ├── Quản lý Đơn hàng → AdminOrderActivity
                    └── Quản lý Người dùng → AdminUserActivity
```

### Các lớp chính

| Lớp | Vai trò |
|---|---|
| `DatabaseHelper` | Tạo và nâng cấp schema SQLite |
| `*DAO` | Thao tác CRUD với từng bảng |
| `*Activity` | Hiển thị UI, xử lý sự kiện |
| `*Adapter` | Bind dữ liệu vào RecyclerView/ListView |
| `*Model` | POJO đại diện cho từng bảng |
| `SessionManager` | Lưu trạng thái đăng nhập qua SharedPreferences |
| `FormatUtils` | Format tiền tệ, ngày tháng |

---

## 4. Công Nghệ Sử Dụng

| Thành phần | Chi tiết |
|---|---|
| Ngôn ngữ | Java |
| Nền tảng | Android SDK (minSdk 24, targetSdk 34) |
| Build tool | Gradle 9.3.1 |
| CSDL | SQLite (qua `SQLiteOpenHelper`) |
| UI | XML Layout, RecyclerView, CardView, ConstraintLayout |
| Ảnh | Drawable resources (PNG/JPG/WEBP) |
| Lưu phiên | SharedPreferences (`SessionManager`) |
| IDE khuyến nghị | Android Studio Hedgehog (2023.1.1) trở lên |

---

## 5. Tính Năng Chính

### Phía Khách Hàng (User)
- ✅ Đăng ký / Đăng nhập / Đăng xuất
- ✅ Xem danh sách đồng hồ, lọc theo hãng
- ✅ Xem chi tiết sản phẩm (ảnh, mô tả, giá)
- ✅ Thêm sản phẩm vào giỏ hàng, chỉnh số lượng
- ✅ Đặt hàng và nhận xác nhận
- ✅ Xem lịch sử đơn hàng và chi tiết từng đơn
- ✅ Chỉnh sửa hồ sơ cá nhân

### Phía Quản Trị (Admin)
- ✅ Dashboard tổng quan
- ✅ CRUD hãng đồng hồ
- ✅ CRUD sản phẩm (kèm ảnh)
- ✅ Xem và cập nhật trạng thái đơn hàng
- ✅ Xem danh sách người dùng

---

## 6. Hướng Dẫn Chạy

### Yêu cầu hệ thống

| Yêu cầu | Phiên bản |
|---|---|
| Android Studio | Hedgehog 2023.1.1 trở lên |
| JDK | 17 trở lên |
| Android SDK | API 24 (Android 7.0) trở lên |
| Gradle | 9.3.1 (tự động tải) |
| Thiết bị / Emulator | Android 7.0+ |

### Bước 1 – Cài môi trường

1. Tải và cài đặt [Android Studio](https://developer.android.com/studio)
2. Trong Android Studio, vào **SDK Manager** và đảm bảo đã cài:
   - Android SDK Platform 34
   - Android Emulator
   - Android SDK Build-Tools 34

```bash
# Kiểm tra Java version
java -version
# Phải >= 17
```

### Bước 2 – Mở dự án

```bash
# Clone repository (nếu đã đưa lên GitHub)
git clone https://github.com/Syy256/BTL_mobile.git

# Hoặc mở trực tiếp trong Android Studio:
# File → Open → chọn thư mục WatchStoreApp/
```

### Bước 3 – Build & Chạy

1. Chờ Android Studio sync Gradle tự động (lần đầu cần Internet)
2. Tạo hoặc kết nối thiết bị:
   - **Emulator**: `Device Manager` → `Create Device` → chọn API 34
   - **Thiết bị thật**: bật `Developer Options` + `USB Debugging`
3. Nhấn nút ▶ **Run** hoặc `Shift+F10`

### Bước 4 – Đăng nhập

Sau khi cài lên thiết bị, sử dụng tài khoản có sẵn:

| Vai trò | Username | Password |
|---|---|---|
| Admin | `admin` | `admin123` |
| User | Tự đăng ký | — |

### Chạy bản APK debug (không cần Android Studio)

```bash
# Sau khi build thành công, APK nằm tại:
app/build/outputs/apk/debug/app-debug.apk

# Cài lên thiết bị qua ADB:
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 7. Cấu Trúc Thư Mục

```
WatchStoreApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/watchstoreapp/
│   │       │   ├── activity/          # Các màn hình UI
│   │       │   │   ├── SplashActivity.java
│   │       │   │   ├── LoginActivity.java
│   │       │   │   ├── RegisterActivity.java
│   │       │   │   ├── MainActivity.java
│   │       │   │   ├── ProductDetailActivity.java
│   │       │   │   ├── CartActivity.java
│   │       │   │   ├── OrderHistoryActivity.java
│   │       │   │   ├── OrderDetailActivity.java
│   │       │   │   ├── ProfileActivity.java
│   │       │   │   ├── AdminDashboardActivity.java
│   │       │   │   ├── AdminBrandActivity.java
│   │       │   │   ├── AdminBrandFormActivity.java
│   │       │   │   ├── AdminProductActivity.java
│   │       │   │   ├── AdminProductFormActivity.java
│   │       │   │   ├── AdminOrderActivity.java
│   │       │   │   └── AdminUserActivity.java
│   │       │   ├── adapter/           # RecyclerView/ListView adapters
│   │       │   │   ├── SanPhamAdapter.java
│   │       │   │   ├── CartAdapter.java
│   │       │   │   ├── HoaDonAdapter.java
│   │       │   │   ├── ChiTietHoaDonAdapter.java
│   │       │   │   ├── AdminHangAdapter.java
│   │       │   │   ├── AdminSanPhamAdapter.java
│   │       │   │   ├── AdminHoaDonAdapter.java
│   │       │   │   └── AdminNguoiDungAdapter.java
│   │       │   ├── dao/               # Data Access Objects (CRUD)
│   │       │   │   ├── NguoiDungDAO.java
│   │       │   │   ├── HangDAO.java
│   │       │   │   ├── SanPhamDAO.java
│   │       │   │   ├── GioHangDAO.java
│   │       │   │   └── HoaDonDAO.java
│   │       │   ├── database/
│   │       │   │   └── DatabaseHelper.java   # SQLiteOpenHelper
│   │       │   ├── model/             # POJO / Entity
│   │       │   │   ├── NguoiDung.java
│   │       │   │   ├── Hang.java
│   │       │   │   ├── SanPham.java
│   │       │   │   ├── GioHang.java
│   │       │   │   ├── HoaDon.java
│   │       │   │   └── ChiTietHoaDon.java
│   │       │   └── utils/
│   │       │       ├── FormatUtils.java      # Format tiền, ngày
│   │       │       └── SessionManager.java   # Quản lý phiên đăng nhập
│   │       ├── res/
│   │       │   ├── drawable/          # Ảnh sản phẩm (casio, seiko, fossil…)
│   │       │   ├── layout/            # XML giao diện các Activity
│   │       │   ├── menu/              # Menu XML
│   │       │   └── values/            # colors, strings, themes
│   │       └── AndroidManifest.xml
│   └── build.gradle                   # Cấu hình module
├── data/
│   └── README_data.md                 # Hướng dẫn: dữ liệu được seed tự động khi chạy
├── demo/
│   └── demo_apk_install.md            # Hướng dẫn cài APK demo nhanh
├── reports/
│   └── BaoCao_WatchStoreApp.pdf       # Báo cáo đồ án
├── slides/
│   └── Slide_WatchStoreApp.pptx       # Slide thuyết trình
├── build.gradle                       # Cấu hình project
├── settings.gradle
├── requirements.txt                   # Không áp dụng (Android/Java; xem phần IDE)
├── .gitignore
└── README.md
```

---

## 8. Tác Giả

| Họ và tên | Mã sinh viên |
|---|---|
| [Đặng Văn Sỹ] | [12523077] | 
| [Đồng Huy Tài] | [12523078] | 


