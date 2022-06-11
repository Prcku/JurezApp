import { Component} from '@angular/core';
import {NgForm} from "@angular/forms";
import {UserService} from "../user.service";
import { Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  email: string | undefined;
  password: string | undefined;

  constructor(private userService: UserService,
              private router: Router) { }

  check(formElemnt: NgForm){
   this.email = formElemnt.value.email;
   this.password = formElemnt.value.password;
   if (formElemnt.invalid){
     return
   }
   // tu bude treba userovu stranku
   this.userService.isAutorized(this.email,this.password).subscribe(
     $data => (this.router.navigate(['/home'])),
      error => (error.message())
   );




  }

}
