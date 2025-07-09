# Raumania Fragrance

Elevate Your Senses with Timeless Scents

At **Raumania Fragrance**, we craft luxurious scents that capture emotions, memories, and moments. Each fragrance is designed to elevate your senses, blending the finest ingredients to create timeless aromas. Our mission is to inspire confidence, celebrate identity, and leave behind something unforgettable—while championing sustainability and ethical sourcing.

## 🛠️ Tech Stack Overview

**Backend:**
- [Spring Boot 3](https://spring.io/projects/spring-boot) (Java 17)
- [MySQL](https://www.mysql.com/) for relational data
- [Redis](https://redis.io/) for caching & sessions
- [Elasticsearch](https://www.elastic.co/elasticsearch/) for product search
- [Stripe](https://stripe.com/) for payments
- [Cloudinary](https://cloudinary.com/) for image hosting
- [JWT](https://jwt.io/) authentication

**Frontend:**
- [Next.js 15](https://nextjs.org/) (React 19, App Router)
- [Tailwind CSS](https://tailwindcss.com/) for styling
- [Three.js](https://threejs.org/) & [@react-three/fiber](https://docs.pmnd.rs/react-three-fiber/getting-started/introduction) for 3D perfume builder
- [Zustand](https://zustand-demo.pmnd.rs/) for state management
- [Radix UI](https://www.radix-ui.com/) components
- [Recharts](https://recharts.org/) for analytics

**Chatbot:**
- [Flask](https://flask.palletsprojects.com/) (Python 3.13)
- [LangChain](https://www.langchain.com/), [Ollama](https://ollama.com/), [HuggingFace](https://huggingface.co/) for LLM Q&A
- [FAISS](https://faiss.ai/) for vector search

**Infrastructure:**
- [Docker Compose](https://docs.docker.com/compose/) for orchestration
- [Nginx](https://nginx.org/) as reverse proxy

## ✨ Main Features

### User Experience
- **Browse & Search:** Discover luxury perfumes, filter by brand, scent, and more
- **3D Perfume Builder:** Design your own fragrance bottle in real-time 3D
- **Product Details:** Rich product pages with images, descriptions, and reviews
- **Cart & Checkout:** Seamless shopping cart, multiple payment methods (Stripe, PayPal, etc.)
- **Order Tracking:** View order history and status
- **Profile Management:** Update personal info, addresses, and passwords
- **Product Reviews:** Share and read reviews
- **Contact & Support:** Reach out via contact form
- **Authentication:** Register, login, password reset, secure JWT auth

### Admin Dashboard
- **Analytics:** Dashboard with sales, users, and product stats
- **Product Management:** Add/edit/delete products, variants, images
- **Brand Management:** Manage fragrance brands
- **Order Management:** View and update orders
- **User Management:** Manage users and roles
- **Review Moderation:** Approve or remove reviews

### AI Chatbot
- **Product Q&A:** Ask about products, prices, variants, and brands
- **Natural Language:** Powered by local LLM (Ollama + LangChain)
- **Contextual Answers:** Uses up-to-date product/brand data
- **Integrated UI:** Chat bubble on every page

## 🚀 Getting Started

### Prerequisites
- [Docker & Docker Compose](https://docs.docker.com/get-docker/)

### Quick Start
```bash
git clone https://github.com/lily-iker/raumania.git
cd raumania
cp client/.env.example client/.env
cp server/.env.example server/.env
# (Edit .env files as needed)
docker-compose up -d
```
- Visit [http://localhost](http://localhost) for the app

## 🎬 Demo

### Home Page
<img src="demo/home-page.gif" alt="Home Page" style="width: 100%; display: block; margin-bottom: 32px;" />

### Product Page
<img src="demo/product-page.gif" alt="Product Page" style="width: 100%; display: block; margin-bottom: 32px;" />

### Custom Perfume Builder
<img src="demo/custom-page.gif" alt="Custom Page" style="width: 100%; display: block; margin-bottom: 32px;" />

### Checkout Flow
<img src="demo/checkout-page.gif" alt="Checkout Page" style="width: 100%; display: block; margin-bottom: 32px;" />

### Chatbot
<img src="demo/chatbot.gif" alt="Chatbot" style="width: 100%; display: block; margin-bottom: 32px;" />

---

### User Screens
<div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 24px; margin-bottom: 40px;">
  <div><img src="demo/user-profile.png" alt="User Profile" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">User Profile</div></div>
  <div><img src="demo/user-profile-2.png" alt="User Profile 2" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">User Profile 2</div></div>
  <div><img src="demo/user-orders.png" alt="User Orders" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">User Orders</div></div>
  <div><img src="demo/user-order-detail.png" alt="User Order Detail" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">User Order Detail</div></div>
  <div><img src="demo/search-page.png" alt="Search Page" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Search Page</div></div>
  <div><img src="demo/contact-page.png" alt="Contact Page" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Contact Page</div></div>
</div>

### Admin Screens
<div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 24px; margin-bottom: 40px;">
  <div><img src="demo/admin-dashboard.png" alt="Admin Dashboard" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Dashboard</div></div>
  <div><img src="demo/admin-products.png" alt="Admin Products" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Products</div></div>
  <div><img src="demo/admin-orders.png" alt="Admin Orders" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Orders</div></div>
  <div><img src="demo/admin-brands.png" alt="Admin Brands" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Brands</div></div>
  <div><img src="demo/admin-users.png" alt="Admin Users" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Users</div></div>
  <div><img src="demo/admin-profile.png" alt="Admin Profile" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Profile</div></div>
  <div><img src="demo/admin-profile-2.png" alt="Admin Profile 2" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Profile 2</div></div>
  <div><img src="demo/admin-reviews.png" alt="Admin Reviews" style="width: 100%; border-radius: 12px;" /><div style="text-align:center; margin-top:8px;">Admin Reviews</div></div>
</div>

## 🙏 Credits
<div>
  <strong>Contributors:</strong>
  <a href="https://github.com/aoi36">
    <img src="https://avatars.githubusercontent.com/u/187289163?v=4" alt="aoi36" style="width: 30px; height: 30px; border-radius: 50%; vertical-align: middle; margin-left: 8px;">
  </a>
  <a href="https://github.com/dungx2">
    <img src="	https://avatars.githubusercontent.com/u/134861029?v=4" alt="dungx2" style="width: 30px; height: 30px; border-radius: 50%; vertical-align: middle; margin-left: 8px;">
  </a>
</div>