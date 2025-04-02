const nextConfig = {
  eslint: {
    ignoreDuringBuilds: true,
  },
  reactStrictMode: true,
  images: {
    domains: ["placehold.co"],
  },
  experimental: {
    trustHostHeader: true,
  },
};

module.exports = nextConfig;
