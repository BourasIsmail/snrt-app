import { NextResponse } from "next/server";
export async function middleware(request) {
  const authRoutes = ["/login"];
  const { pathname, origin } = request.nextUrl;
  const token = request.cookies.get("token")?.value;

  const url = request.nextUrl.clone();
  url.pathname =
    token && authRoutes.includes(pathname)
      ? "/"
      : !token && pathname !== "/login"
      ? "/login"
      : pathname;

  url.host = request.headers.get("host");

  return pathname !== url.pathname
    ? NextResponse.redirect(url)
    : NextResponse.next();
}

export const config = {
  matcher: "/((?!api|admin|static|.*\\..*|_next).*)",
};
