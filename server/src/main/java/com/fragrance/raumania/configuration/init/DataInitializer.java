package com.fragrance.raumania.configuration.init;

import com.fragrance.raumania.constant.delivery.DeliveryMethod;
import com.fragrance.raumania.constant.delivery.DeliveryStatus;
import com.fragrance.raumania.constant.order.OrderStatus;
import com.fragrance.raumania.constant.payment.PaymentMethod;
import com.fragrance.raumania.constant.payment.PaymentStatus;
import com.fragrance.raumania.constant.role.RoleName;
import com.fragrance.raumania.mapper.ProductMapper;
import com.fragrance.raumania.model.authorization.Role;
import com.fragrance.raumania.model.order.Order;
import com.fragrance.raumania.model.order.OrderItem;
import com.fragrance.raumania.model.payment.Payment;
import com.fragrance.raumania.model.product.*;
import com.fragrance.raumania.model.user.User;
import com.fragrance.raumania.repository.*;
import com.fragrance.raumania.service.DataExportService;
import com.fragrance.raumania.service.interfaces.BrandService;
import com.fragrance.raumania.service.interfaces.ProductIndexService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ReviewRepository reviewRepository;
    private final ProductDocumentRepository productDocumentRepository;
    private final ProductMapper productMapper;
    private final ProductImageRepository productImageRepository;
    private final DataExportService dataExportService;
    private final ProductIndexService productIndexService;
    private final BrandService brandService;
    private final OrderRepository orderRepository;

    @PostConstruct
    public void initData() {
        initRoles();
        initAdminAccount();
        initNormalUsers();
        initBrands();
        initProductsAndVariants();
        initReviews();
        initOrders();
        initProductDocuments();
    }

    @EventListener(ApplicationReadyEvent.class)
    @Async
    public void exportDataForChatbotEventListener() throws InterruptedException {
        Thread.sleep(10000);
        exportDataForChatbot();
    }

    public void initRoles() {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
        }
        System.out.println("✅ Roles initialized.");
    }

    public void initAdminAccount() {
        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseGet(() -> roleRepository.save(Role.builder().name(RoleName.ADMIN).build())); // Use orElseGet to create if not found

        Optional<User> adminUserOpt = userRepository.findByUsername("adminadmin");

        if (adminUserOpt.isEmpty()) {
            User adminUser = User.builder()
                    .fullName("Admin User")
                    .email("adminadmin@gmail.com")
                    .username("adminadmin")
                    .password(passwordEncoder.encode("adminadmin"))
                    .isActive(true)
                    .role(adminRole)
                    .build();

            userRepository.save(adminUser);
            System.out.println("✅ Admin user created.");
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }

    public void initNormalUsers() {
        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(RoleName.USER).build()));

        List<User> normalUsers = List.of(
                User.builder()
                        .fullName("John Smith")
                        .email("john.smith@gmail.com")
                        .username("johnsmith")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567890")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("Emily Johnson")
                        .email("emily.johnson@gmail.com")
                        .username("emilyjohnson")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567891")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("Michael Brown")
                        .email("michael.brown@gmail.com")
                        .username("michaelbrown")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567892")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("Sarah Davis")
                        .email("sarah.davis@gmail.com")
                        .username("sarahdavis")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567893")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("David Wilson")
                        .email("david.wilson@gmail.com")
                        .username("davidwilson")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567894")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("Lisa Anderson")
                        .email("lisa.anderson@gmail.com")
                        .username("lisaanderson")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567895")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("James Taylor")
                        .email("james.taylor@gmail.com")
                        .username("jamestaylor")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567896")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build(),
                User.builder()
                        .fullName("Jennifer Martinez")
                        .email("jennifer.martinez@gmail.com")
                        .username("jennifermartinez")
                        .password(passwordEncoder.encode("password123"))
                        .phoneNumber("+1234567897")
                        .isActive(true)
                        .emailVerified(true)
                        .role(userRole)
                        .build()
        );

        normalUsers.forEach(user -> {
            Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
            if (existingUser.isEmpty()) {
                userRepository.save(user);
                System.out.println("✅ Normal user created: " + user.getFullName());
            } else {
                System.out.println("ℹ️ User already exists: " + user.getUsername());
            }
        });

        System.out.println("✅ Normal users initialization complete.");
    }

    public void initBrands() {
        // List of brands to initialize
        List<Brand> brands = List.of(
                new Brand(null, "Skibidi Toilet", "A unique fragrance inspired by the Skibidi Toilet phenomenon.", new HashSet<>()),
                new Brand(null, "Lavender Dream", "A soothing lavender scent perfect for relaxation.", new HashSet<>()),
                new Brand(null, "Oceanic Breeze", "A fresh and crisp fragrance inspired by the sea.", new HashSet<>()),
                new Brand(null, "Golden Spice", "An exotic, warm fragrance with spicy undertones.", new HashSet<>()),
                new Brand(null, "Floral Blossom", "A refreshing floral fragrance with notes of rose and jasmine.", new HashSet<>()),
                new Brand(null, "Mystic Woods", "A deep and earthy fragrance with a woody base.", new HashSet<>()),
                new Brand(null, "Citrus Splash", "A bright, citrus-infused fragrance for an energetic vibe.", new HashSet<>())
        );

        // Save each brand if not already present
        brands.forEach(brand -> {
            brandRepository.findByName(brand.getName()).ifPresentOrElse(
                    existingBrand -> System.out.println("ℹ️ Brand already exists: " + brand.getName()),
                    () -> {
                        brandRepository.save(brand);
                        System.out.println("✅ Brand initialized: " + brand.getName());
                    }
            );
        });

        System.out.println("✅ Brand initialization complete.");
    }

    public void initProductsAndVariants() {
        List<Brand> brands = brandRepository.findAll();

        List<Product> products = new ArrayList<>();

        String[] names = {
                "Mystic Essence", "Velvet Bloom", "Amber Whisper", "Ocean Breeze", "Golden Spice",
                "Crimson Dusk", "Silver Mist", "Twilight Fern", "Lunar Petal", "Sunlit Amber",
                "Ivory Sands", "Noir Blossom", "Azure Mist", "Opal Dream", "Scarlet Haven",
                "Emerald Glow", "Golden Horizon", "Celestial Muse", "Winter Citrus", "Orchid Mirage",
                "Radiant Bloom", "Dusky Rose", "Amber Twilight", "Frosted Bloom", "Velvet Sunrise",
                "Serene Waters", "Wild Citrus", "Hidden Garden", "Coral Whisper", "Midnight Silk",
                "Aurora Veil", "Amber Lush", "Dewy Bloom", "Rose Cascade", "Tropical Kiss", "Savannah Air"
        };

        String[] descriptions = {
                "A captivating fragrance with a delicate balance of rare floral notes and warm accents.",
                "An enchanting fusion of fresh blooms and dewy petals capturing the essence of spring.",
                "A warm and woody fragrance that envelops you in sophistication and intrigue.",
                "Dive into a refreshing scent reminiscent of a cool ocean breeze and salt spray.",
                "An intoxicating blend of exotic spices and luxurious oriental aromas.",
                "A delicate mist of silvered florals and soft musky undertones.",
                "A vibrant green note with fern, herbs, and morning dew.",
                "Soft petals kissed by moonlight, subtle and deeply romantic.",
                "Warm amber meets golden sands in this radiant blend.",
                "Soft citrus blooms with creamy undertones, perfect for daily wear.",
                "Crushed ivory petals with touches of sunlight and warmth.",
                "Dark flowers and mysterious musk for an unforgettable evening.",
                "Crisp ocean mist entwined with light florals for a breezy finish.",
                "Dreamy florals in a shimmering, otherworldly blend.",
                "A lush, crimson bouquet wrapped in vanilla whispers.",
                "Rich emerald leaves crushed with a hint of sweet neroli.",
                "Sun-warmed spices blended with rare aromatic woods.",
                "Star-kissed petals and golden woods for an ethereal scent.",
                "Refreshing citrus touched by winter snowflakes.",
                "Orchids wrapped in velvety amber sweetness.",
                "Radiant fields of flowers under a sunny sky.",
                "A dusky, mysterious rose with hints of oud.",
                "Deep amber with fleeting touches of jasmine.",
                "Frosted petals mingling with crisp, icy air.",
                "A gentle sunrise captured in a velvet floral bouquet.",
                "Serene aquatic notes blended with soft white musk.",
                "Zesty citrus with hints of wild greenery.",
                "Hidden garden blossoms with secret, spicy trails.",
                "Coral petals dipped in fresh morning mist.",
                "Smooth silk touched by midnight blossoms.",
                "The colors of the aurora captured in perfumed elegance.",
                "Golden amber layered with lush florals.",
                "Morning dew and delicate blooms dancing together.",
                "A cascading river of roses and crystal-clear waters.",
                "Tropical fruits and lush florals intertwining gracefully.",
                "Wild grasses and sun-warmed fields in a summer breeze."
        };

        String[] materials = {
                "Artisan ingredients and rare essences",
                "Fresh petals and natural extracts",
                "Rich resins and amber oils",
                "Marine extracts and essential minerals",
                "Exotic spices and rare resins",
                "Silvered florals and misty musk",
                "Green herbs and dewy leaves",
                "Velvet petals and lunar drops",
                "Amber crystals and golden woods",
                "Citrus zest and creamy petals"
        };

        String[] inspirations = {
                "Inspired by the mysteries of the universe",
                "Inspired by blooming gardens in the spring",
                "Inspired by ancient traditions",
                "Inspired by the endless ocean",
                "Inspired by ancient spice routes and oriental traditions",
                "Inspired by moonlit nights and silver skies",
                "Inspired by lush green forests",
                "Inspired by secret midnight gardens",
                "Inspired by sunlit beaches",
                "Inspired by crisp mountain air"
        };

        String[] usageInstructions = {
                "Apply sparingly on pulse points for a lasting aroma",
                "Spray lightly over the body for even coverage",
                "Apply on wrists and neck and reapply as needed",
                "Apply after a shower for a refreshing effect",
                "A little goes a long way; apply sparingly"
        };

        List<String> randomImages = List.of(
                "https://fimgs.net/mdimg/news/en/22537/social.22537-1024x536.jpg?t=1745778302",
                "https://fimgs.net/mdimg/news/en/22520/social.22520-1024x536.jpg?t=1745537255",
                "https://fimgs.net/mdimg/news/en/22510/social.22510-1024x536.jpg?t=1745425936",
                "https://fimgs.net/mdimg/news/en/22517/social.22517-1024x536.jpg?t=1745528406",
                "https://fimgs.net/mdimg/news/en/22537/social.22537-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22516/social.22516-1024x536.jpg?t=1745511302",
                "https://fimgs.net/mdimg/news/en/22514/social.22514-1024x536.jpg?t=1745496902",
                "https://fimgs.net/mdimg/news/en/22534/social.22534-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22524/social.22524-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22523/social.22523-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22521/social.22521-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22519/social.22519-1024x536.jpg?t=1745533382",
                "https://fimgs.net/mdimg/news/en/22519/social.22519-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22525/social.22525-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22532/social.22532-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22514/social.22514-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22526/social.22526-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22529/social.22529-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22517/social.22517-1024x536.jpg",
                "https://fimgs.net/mdimg/news/en/22516/social.22516-1024x536.jpg",
                "https://fimgs.net/photogram/p640/ud/qj/wzEf8ceuYHb48xXM.jpg",
                "https://fimgs.net/photogram/p640/81/0l/aFbA31rq5TVp6PpG.jpg",
                "https://fimgs.net/photogram/p640/lf/j7/r0b0JM9Ch0x5hXUx.jpg",
                "https://fimgs.net/photogram/p640/ok/0i/BiJYUXy0vFhQOkS8.jpg",
                "https://fimgs.net/photogram/p640/vu/pr/IjYxKokE6rHwOn6g.jpg",
                "https://fimgs.net/mdimg/news/en/22524/social.22524-640x335.jpg?t=1745599044",
                "https://fimgs.net/mdimg/news/en/22529/social.22529-640x335.jpg?t=1745676782",
                "https://fimgs.net/mdimg/news/en/22531/social.22531-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22521/social.22521-640x335.jpg?t=1745544241",
                "https://fimgs.net/mdimg/news/en/22520/social.22520-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22519/social.22519-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22521/social.22521-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22529/social.22529-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22517/social.22517-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22533/social.22533-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22537/social.22537-640x335.jpg",
                "https://fimgs.net/mdimg/news/en/22526/social.22526-640x335.jpg",
                "https://theme.hstatic.net/1000340570/1000964732/14/img-smb-gift.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/img-smb-niche.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/img-smb-nu.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner_brand_image_section_01.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner_brand_image_section_03.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner-nu-desk.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner-nam-desk.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner-niche-desk.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner_image_section_02.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner_image_section_03.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_bodyhomecare_1.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_nu_2.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_nam_2.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_nam_3.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_brand_1.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_nu_3.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_brand_3.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_nu_1.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_nam_1.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/menu_hover_brand_2.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner-niche-mob.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner-nu-mob.jpg?v=6998",
                "https://theme.hstatic.net/1000340570/1000964732/14/banner-nam-mob.jpg?v=6998",
                "https://product.hstatic.net/1000340570/product/tommy_hilfiger_edt_57f62edaa1d8422fa3b0c11171f7fa7c_grande.jpg",
                "https://product.hstatic.net/1000340570/product/dsquared2-icon-pour-homme_614e728ebee040f1a2aeed2ac78380af_grande.jpg",
                "https://product.hstatic.net/1000340570/product/gio-trang-100ml_8d628d81bf53494eaccf931159e7432f_grande.jpg",
                "https://product.hstatic.net/1000340570/product/dsquared2-icon-pour-femme_497b41b8f9c24158986ab5a219cfe7b0_grande.jpg",
                "https://product.hstatic.net/1000340570/product/versace-eros-for-men_e21f596ba1f6467eb39ace8813943882_grande.jpg",
                "https://product.hstatic.net/1000340570/product/versace-bright-crystal_405b9f59113d48dea5e79fd1dd3a99e9_grande.jpg",
                "https://product.hstatic.net/1000340570/product/kich-thuoc_10791e64a61e43fd8668d7294b88e6b4_grande.jpg",
                "https://product.hstatic.net/1000340570/product/giorgio-armani-acqua-di-gioia-intense_3bd0ce1c7a6e4ab09050dc491ee677e5_grande.jpg",
                "https://product.hstatic.net/1000340570/product/kich-thuoc_7f2119ebaa5b40b4a1c7949873b9eb04_grande.jpg",
                "https://product.hstatic.net/1000340570/product/marc-jacobs-eau-so-fresh-mini-size_351a28fbaa354352b26b440bc660d5ee_grande.jpg",
                "https://product.hstatic.net/1000340570/product/versace-pour-homme_aac4a84bdbdf400fb3140f176b64373a_grande.jpg",
                "https://product.hstatic.net/1000340570/product/mini-moi_59ec6273d0b643808b82ad3f12658104_grande.jpg",
                "https://product.hstatic.net/1000340570/product/gucci-magnolia-mini-size_af0210969fad4cbb83dc38a0508377f1_grande.jpg",
                "https://product.hstatic.net/1000340570/product/gucci-flora-gorgeous-jasmine-mini-size_2dfddfdb0b734a1298e4542938354742_grande.jpg",
                "https://product.hstatic.net/1000340570/product/gucci-bloom-eau-de-parfum_eb88a1912db246a48d4bdc1be06aa807_grande.jpg",
                "https://product.hstatic.net/1000340570/product/dylan_90ca7824913645f08e621adb9299950b_grande.jpg",
                "https://product.hstatic.net/1000340570/product/sig-edp_5520e41e042a4fa4a7757fd0d4d202bb_grande.jpg",
                "https://product.hstatic.net/1000340570/product/toy-2_e8763e1a84c740abbec90c909ddc4ed5_grande.jpg",
                "https://product.hstatic.net/1000340570/product/gucci-bloom_02f6ff28224143c6a358b788b3e4911c_grande.jpg",
                "https://product.hstatic.net/1000340570/product/flam_28e70e4357b44e38820431a9e7d0b3b0_grande.jpg",
                "https://product.hstatic.net/1000340570/product/vsph_aecdee029ccf43e9992be08c88374539_grande.jpg",
                "https://product.hstatic.net/1000340570/product/salvatore_ferragamo_red_leather_399f9a2c9ddd455a96b79592c322b0e5_grande.jpg",
                "https://product.hstatic.net/1000340570/product/giorgio-armani-code-pour-homme-eau-de-parfum-125ml_f2458c01cabe401fa9a1af2691985912_grande.jpg",
                "https://product.hstatic.net/1000340570/product/versace-eros-shower-gel_1c22030a52e9422db52e38ec28d05df9_grande.jpg",
                "https://product.hstatic.net/1000340570/product/tommy_hilfiger_tommy_girl_786135da321a410698aa6c4d3d8c1681_grande.jpg",
                "https://product.hstatic.net/1000340570/product/nuoc-hoa-paco-rabane-mini-1-million_43e1f19f323f46f0838a996fd68e7ff6_grande.jpg",
                "https://product.hstatic.net/1000340570/product/versace-pour-homme-hair_-body-shampoo_a0ba709ffcce4ee9941ef853a371a116_grande.jpg",
                "https://product.hstatic.net/1000340570/product/52cf668d76f149fd9d5f3f411859fad4_ab790b93987f470ab92023c39c492477_grande.jpg",
                "https://product.hstatic.net/1000340570/product/versace-bright-crystal-perfume-bath-_-shower-gel_ce4e0409f224425eadb4740d5e8c3fcc_grande.jpg"
        );

        Random random = new Random();

        for (int i = 0; i < 36; i++) {
            Brand randomBrand = brands.get(random.nextInt(brands.size()));

            Product product = Product.builder()
                    .name(names[i])
                    .description(descriptions[i])
                    .productMaterial(materials[random.nextInt(materials.length)])
                    .inspiration(inspirations[random.nextInt(inspirations.length)])
                    .usageInstructions(usageInstructions[random.nextInt(usageInstructions.length)])
                    .thumbnailImage(randomImages.get(random.nextInt(randomImages.size())))
                    .isActive(true)
                    .brand(randomBrand)
                    .build();
            products.add(product);
        }

        products.forEach(product -> {
            Optional<Product> existingProduct = productRepository.findByName(product.getName());

            if (existingProduct.isEmpty()) {
                Product savedProduct = productRepository.save(product);
                initProductVariants(savedProduct);

                List<ProductImage> images = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    String randomImageUrl = randomImages.get(random.nextInt(randomImages.size()));
                    ProductImage productImage = ProductImage.builder()
                            .image(randomImageUrl)
                            .product(savedProduct)
                            .build();
                    images.add(productImage);
                }
                productImageRepository.saveAll(images);

                System.out.println("✅ Product initialized: " + savedProduct.getName());
            } else {
                System.out.println("ℹ️ Product already exists: " + product.getName());
            }
        });

        System.out.println("✅ Product initialization complete.");
    }

    public void initProductVariants(Product product) {
        Random random = new Random();

        String[] variantTypes = {"Travel Size", "Deluxe", "Collector’s Edition", "Signature Edition", "Limited Batch", "Essence Collection"};
        String[] sizes = {"50ml", "100ml", "150ml"};
        String[] scents = {"Woody Amber", "Floral Musk", "Citrus Spice", "Ocean Breeze", "Mystic Oud", "Velvet Rose"};

        List<ProductVariant> variants = new ArrayList<>();

        // Create 2 to 4 random variants for each product
        int variantCount = 2 + random.nextInt(3);

        for (int i = 0; i < variantCount; i++) {
            String variantName = product.getName() + " - " + variantTypes[random.nextInt(variantTypes.length)];
            String size = sizes[random.nextInt(sizes.length)];
            String scent = scents[random.nextInt(scents.length)];
            double price = 20 + (80 * random.nextDouble()); // Random price between 20 and 100
            int stock = 20 + random.nextInt(51); // Stock between 20 and 70

            ProductVariant variant = ProductVariant.builder()
                    .name(variantName)
                    .size(size)
                    .scent(scent)
                    .stock(stock)
                    .price(Math.round(price * 100.0) / 100.0)
                    .product(product)
                    .build();

            variants.add(variant);
        }

        Double minPrice = null;
        Double maxPrice = null;

        for (ProductVariant variant : variants) {
            Optional<ProductVariant> existingVariant = productVariantRepository.findByNameAndProduct(variant.getName(), product);
            if (existingVariant.isEmpty()) {
                productVariantRepository.save(variant);
                System.out.println("✅ Product variant initialized: " + variant.getName());

                double price = variant.getPrice();
                minPrice = (minPrice == null || price < minPrice) ? price : minPrice;
                maxPrice = (maxPrice == null || price > maxPrice) ? price : maxPrice;
            } else {
                System.out.println("ℹ️ Product variant already exists: " + variant.getName());
            }
        }

        // Update product minPrice and maxPrice if we added new variants
        if (minPrice != null && maxPrice != null) {
            product.setMinPrice(minPrice);
            product.setMaxPrice(maxPrice);
            productRepository.save(product);  // Persist changes to the product
            System.out.println("🔄 Product price range updated: " + product.getName() + " [Min: " + minPrice + ", Max: " + maxPrice + "]");
        }
    }

    public void initReviews() {
        List<User> normalUsers = userRepository.findByRoleName(RoleName.USER);
        if (normalUsers.isEmpty()) {
            System.out.println("⚠️ No normal users found. Skipping additional review initialization.");
            return;
        }

        List<Product> products = productRepository.findAllProductsWithBrandAndVariants();
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found. Skipping additional review initialization.");
            return;
        }

        Random random = new Random();
        String[] reviewComments = {
                "Absolutely love this fragrance! It's become my signature scent.",
                "Great quality and long-lasting. Highly recommend!",
                "The scent is beautiful but a bit too strong for everyday wear.",
                "Perfect for special occasions. Very elegant and sophisticated.",
                "Good value for money. The fragrance is pleasant and not overwhelming.",
                "Not my favorite. The scent doesn't last as long as I hoped.",
                "Amazing! I get compliments every time I wear this.",
                "The packaging is beautiful and the scent is divine.",
                "A bit too floral for my taste, but good quality overall.",
                "Love the unique blend of scents. Very refreshing!",
                "This fragrance is perfect for summer. Light and airy.",
                "Excellent longevity and projection. Worth every penny!",
                "The scent is okay but nothing extraordinary.",
                "Beautiful fragrance that makes me feel confident.",
                "A bit pricey but the quality justifies the cost.",
                "Perfect for date nights. Very romantic and alluring.",
                "The scent is too sweet for my liking.",
                "Great fragrance for the office. Professional yet pleasant.",
                "Love how this fragrance evolves throughout the day.",
                "Not what I expected based on the description."
        };

        int reviewCount = 0;
        for (Product product : products) {
            if (reviewCount >= 50) break; // Limit total reviews

            List<ProductVariant> variants = new ArrayList<>(product.getProductVariants());
            if (variants.isEmpty()) continue;

            // Add 1-3 reviews per product
            int productReviewCount = 1 + random.nextInt(3);
            for (int i = 0; i < productReviewCount && reviewCount < 50; i++) {
                User randomUser = normalUsers.get(random.nextInt(normalUsers.size()));
                ProductVariant randomVariant = variants.get(random.nextInt(variants.size()));
                int rating = 1 + random.nextInt(5); // 1-5 stars
                String comment = reviewComments[random.nextInt(reviewComments.length)];

                // Check if this user already reviewed this product
                boolean alreadyReviewed = reviewRepository.existsByUserIdAndProductId(randomUser.getId(), product.getId());
                if (!alreadyReviewed) {
                    Review review = Review.builder()
                            .rating(rating)
                            .content(comment)
                            .user(randomUser)
                            .productVariant(randomVariant)
                            .product(product)
                            .build();

                    reviewRepository.save(review);
                    reviewCount++;
                    System.out.println("✅ Review created: " + rating + " stars for " + product.getName() + " by " + randomUser.getFullName());
                }
            }
        }

        System.out.println("✅ Reviews initialization complete. Total reviews added: " + reviewCount);
    }

    public void initOrders() {
        List<User> normalUsers = userRepository.findByRoleName(RoleName.USER);
        if (normalUsers.isEmpty()) {
            System.out.println("⚠️ No normal users found. Skipping order initialization.");
            return;
        }

        List<ProductVariant> productVariants = productVariantRepository.findAllWithProduct();
        if (productVariants.isEmpty()) {
            System.out.println("⚠️ No product variants found. Skipping order initialization.");
            return;
        }

        Random random = new Random();
        OrderStatus[] orderStatuses = OrderStatus.values();
        PaymentStatus[] paymentStatuses = PaymentStatus.values();
        PaymentMethod[] paymentMethods = PaymentMethod.values();
        DeliveryMethod[] deliveryMethods = DeliveryMethod.values();
        DeliveryStatus[] deliveryStatuses = DeliveryStatus.values();

        String[] cities = {"Ho Chi Minh City", "Hanoi", "Da Nang", "Can Tho", "Hai Phong", "Bien Hoa", "Hue", "Nha Trang"};
        String[] streets = {"Nguyen Hue", "Le Loi", "Tran Hung Dao", "Hai Ba Trung", "Dong Khoi", "Nam Ky Khoi Nghia", "Vo Van Tan", "Cach Mang Thang Tam"};

        // Create 15-20 orders
        int orderCount = 15 + random.nextInt(6);
        for (int i = 0; i < orderCount; i++) {
            User randomUser = normalUsers.get(random.nextInt(normalUsers.size()));

            // Create order
            Order order = Order.builder()
                    .orderStatus(orderStatuses[random.nextInt(orderStatuses.length)])
                    .paymentStatus(paymentStatuses[random.nextInt(paymentStatuses.length)])
                    .deliveryMethod(deliveryMethods[random.nextInt(deliveryMethods.length)])
                    .deliveryStatus(deliveryStatuses[random.nextInt(deliveryStatuses.length)])
                    .deliveryFee(15.0 + (35.0 * random.nextDouble())) // 15-50 delivery fee
                    .houseNumber(String.valueOf(1 + random.nextInt(999)))
                    .streetName(streets[random.nextInt(streets.length)])
                    .city(cities[random.nextInt(cities.length)])
                    .state("Vietnam")
                    .country("Vietnam")
                    .postalCode(String.valueOf(10000 + random.nextInt(90000)))
                    .user(randomUser)
                    .build();

            // Add 1-4 order items
            List<OrderItem> orderItems = new ArrayList<>();
            int itemCount = 1 + random.nextInt(4);
            double totalAmount = 0.0;

            for (int j = 0; j < itemCount; j++) {
                ProductVariant randomVariant = productVariants.get(random.nextInt(productVariants.size()));
                Product product = randomVariant.getProduct();
                int quantity = 1 + random.nextInt(3); // 1-3 quantity
                double unitPrice = randomVariant.getPrice();
                double totalPrice = unitPrice * quantity;
                totalAmount += totalPrice;

                OrderItem orderItem = OrderItem.builder()
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .totalPrice(totalPrice)
                        .productName(product.getName())
                        .productDescription(product.getDescription())
                        .productThumbnail(product.getThumbnailImage())
                        .productVariantName(randomVariant.getName())
                        .productVariantSize(randomVariant.getSize())
                        .productVariantScent(randomVariant.getScent())
                        .productVariantId(randomVariant.getId())
                        .productId(product.getId())
                        .order(order)
                        .build();

                orderItems.add(orderItem);
            }

            totalAmount += order.getDeliveryFee();
            order.setTotalAmount(Math.round(totalAmount * 100.0) / 100.0);
            order.setOrderItems(orderItems);

            // Create payment
            Payment payment = Payment.builder()
                    .paymentAmount(order.getTotalAmount())
                    .paymentMethod(paymentMethods[random.nextInt(paymentMethods.length)])
                    .paymentStatus(order.getPaymentStatus())
                    .order(order)
                    .build();

            order.setPayment(payment);

            // Save order (cascade will save order items and payment)
            Order savedOrder = orderRepository.save(order);
            System.out.println("✅ Order created: " + savedOrder.getId() + " for user " + randomUser.getFullName() + " - Total: $" + savedOrder.getTotalAmount());
        }

        System.out.println("✅ Order initialization complete. Total orders created: " + orderCount);
    }

    public void initProductDocuments() {

        if (productDocumentRepository.count() > 0) {
            System.out.println("✅ Product documents already initialized. Skipping initialization.");

            return;
        }

        List<Product> products = productRepository.findAllProductsWithBrandAndVariants();
        List<ProductDocument> productDocuments = new ArrayList<>();

        for (Product product : products) {
            ProductDocument productDocument = productMapper.toDocument(product);
            productDocuments.add(productDocument);
            System.out.println("✅ Product document ready for saving: " + product.getName());
            System.out.println("✅ Product id document ready for saving: " + product.getId());
        }

        // Save all documents at once
        productDocumentRepository.saveAll(productDocuments);
        System.out.println("✅ Product document initialization complete.");
    }

    public void exportDataForChatbot() {
        System.out.println("Exporting product.json...");
        dataExportService.exportData("product.json", productIndexService.getAllForDataExport());
        System.out.println("Exporting brand.json...");
        dataExportService.exportData("brand.json", brandService.getAllForDataExport());
    }
}