package ma.snrt.snrt.Services;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final MaterielService materielService;
    private final UniteService uniteService;
    private final UsersService usersService;
    private final RolesService rolesService;
    private final TypeService typeService;

    public DashboardService(MaterielService materielService, UniteService uniteService, UsersService usersService, RolesService rolesService, TypeService typeService) {
        this.materielService = materielService;
        this.uniteService = uniteService;
        this.usersService = usersService;
        this.rolesService = rolesService;
        this.typeService = typeService;
    }

    public int getMaterielsCount() {
        return materielService.getMateriels().size();
    }

    public int getUnitesCount() {
        return uniteService.getUnites().size();
    }

    public int getUsersCount() {
        return usersService.getUsers().size();
    }

    public int getRolesCount() {
        return rolesService.getRoles().size();
    }

    public int getTypesCount() {
        return typeService.getTypes().size();
    }

    public Object getDashboardData() {
        return new Object() {
            public int materielsCount = getMaterielsCount();
            public int unitesCount = getUnitesCount();
            public int usersCount = getUsersCount();
            public int rolesCount = getRolesCount();
            public int typesCount = getTypesCount();
        };
    }

    public Object getDashboardByType(){
        return materielService.getMaterielByType();
    }

    public Object getDashboardByUnite(){
        return materielService.getMaterielByUnite();
    }

    public Object getDashboardByUniteAndType(){
        return materielService.getMaterielByUniteAndType();
    }


}
