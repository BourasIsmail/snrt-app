"use client"

import { useState, useEffect } from "react"
import Link from "next/link"
import { motion } from "framer-motion"
import { ChevronLeft, ChevronRight, LayoutDashboard, Users, Box, User as Icone, Settings, LogOut } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from "@/components/ui/tooltip"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { getCurrentUsers, logout } from "@/app/api"
import { EditProfileForm } from "@/components/edit-profile-form"
import type { User } from "@/app/types/User"
import { useToast } from "@/hooks/use-toast"

interface SidebarProps {
  onToggle: (isOpen: boolean) => void
}

export function Sidebar({ onToggle }: SidebarProps) {
  const [isOpen, setIsOpen] = useState(true)
  const [isEditProfileOpen, setIsEditProfileOpen] = useState(false)
  const { toast } = useToast()
  const [user, setUser] = useState<User | null>(null)

  useEffect(() => {
    fetchUserProfile()
  }, [])

  const fetchUserProfile = async () => {
    try {
      const userData = await getCurrentUsers()
      setUser(userData)
    } catch (error) {
      console.error("Failed to fetch user profile:", error)
      toast({
        title: "Error",
        description: "Failed to fetch user profile",
        variant: "destructive",
      })
    }
  }

  useEffect(() => {
    onToggle(isOpen)
  }, [isOpen, onToggle])

  const is_Super_Admin = user?.roles?.map((role) => role.role).includes("SUPER_ADMIN_ROLES")

  const menuItems = [
    { icon: LayoutDashboard, label: "Tableau de bord", href: "/" },
    { icon: Box, label: "Gestion des unités", href: "/unite" },
    ...(is_Super_Admin ? [{ icon: Users, label: "Gestion des utilisateurs", href: "/users" }] : []),
  ]

  return (
      <TooltipProvider>
        <motion.div
            className="fixed left-0 top-0 bottom-0 z-40 flex flex-col bg-background border-r"
            animate={{ width: isOpen ? 250 : 60 }}
            transition={{ duration: 0.3 }}
        >
          <Button
              variant="ghost"
              size="icon"
              className="absolute top-4 -right-4 bg-background border rounded-full"
              onClick={() => setIsOpen(!isOpen)}
          >
            {isOpen ? <ChevronLeft size={16} /> : <ChevronRight size={16} />}
          </Button>
          <div className="flex-1 py-8 flex flex-col space-y-4">
            {menuItems.map((item) => (
                <Tooltip key={item.href}>
                  <TooltipTrigger asChild>
                    <Link href={item.href} passHref>
                      <Button variant="ghost" className={`w-full ${isOpen ? "justify-start px-4" : "justify-center px-0"}`}>
                        <item.icon className={`h-5 w-5 ${isOpen ? "mr-2" : ""}`} />
                        {isOpen && <span>{item.label}</span>}
                      </Button>
                    </Link>
                  </TooltipTrigger>
                  {!isOpen && <TooltipContent side="right">{item.label}</TooltipContent>}
                </Tooltip>
            ))}
          </div>
          <Popover>
            <Tooltip>
              <TooltipTrigger asChild>
                <PopoverTrigger asChild>
                  <Button
                      variant="ghost"
                      className={`w-full mb-4 ${isOpen ? "justify-start px-4" : "justify-center px-0"}`}
                  >
                    <Icone className={`h-5 w-5 ${isOpen ? "mr-2" : ""}`} />
                    {isOpen && <span>Profil</span>}
                  </Button>
                </PopoverTrigger>
              </TooltipTrigger>
              {!isOpen && <TooltipContent side="right">Profil</TooltipContent>}
            </Tooltip>
            <PopoverContent className="w-56" align={isOpen ? "start" : "center"}>
              <div className="flex flex-col space-y-2">
                <Button variant="ghost" className="justify-start" onClick={() => setIsEditProfileOpen(true)}>
                  <Settings className="h-4 w-4 mr-2" />
                  Modifier le profil
                </Button>
                <Button onClick={logout} variant="ghost" className="justify-start">
                  <LogOut className="h-4 w-4 mr-2" />
                  Déconnexion
                </Button>
              </div>
            </PopoverContent>
          </Popover>

          <Dialog open={isEditProfileOpen} onOpenChange={setIsEditProfileOpen}>
            <DialogContent>
              <DialogHeader>
                <DialogTitle>Modifier le profil</DialogTitle>
              </DialogHeader>
              <EditProfileForm onClose={() => setIsEditProfileOpen(false)} />
            </DialogContent>
          </Dialog>
        </motion.div>
      </TooltipProvider>
  )
}