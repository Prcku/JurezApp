import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import {UserService} from "./user.service";
import {User} from "./user";
import {LocalStorageService} from "angular-2-local-storage";


@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  private user: User | undefined;
  constructor(private userService: UserService,
  private router: Router,
  private localStorageService: LocalStorageService) {
    userService.onUserChange()
      .subscribe(value => {
        this.user = value;
      })
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.user?.token != undefined){
      console.log("som tam kde normalne")
      return true;
    }
    else if (this.localStorageService.get("token")){
      console.log("som tam kde je local storage")
      return true;
    }
    else {
      this.router.navigate(['/login'])
      return false;
    }
  }
}
