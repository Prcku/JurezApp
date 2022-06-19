import { Component} from '@angular/core';
import {NgForm} from "@angular/forms";
import {UserService} from "../user.service";
import { Router} from "@angular/router";
import {UserDTO} from "../userDTO";
import {error} from "jquery";
import {stringify} from "@angular/compiler/src/util";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  item = {} as UserDTO;
  error: string | undefined;
  constructor(private userService: UserService,
              private router: Router) {
    this.error = undefined;
  }

  check(formElemnt: NgForm){
    this.item.email = formElemnt.value.email;
    this.item.password = formElemnt.value.password;

   if (formElemnt.invalid){
     return
   }
   // admin
    if (this.item.email == "branislavsocha159@gmail.com" && this.item.password == "123"){
      this.userService.isAutorized(this.item).subscribe(value => {
        if (value) {
          this.router.navigate(['/adminpage'])
        }
      })
      return;
    }
    //user
   this.userService.isAutorized(this.item).subscribe(value => {
     if (value){
       this.router.navigate(['/home'])
     }
     },
     error => {
        if (error){
          this.error = error;
        }
     }
   )

      // error => (console.log(error))





  }

}
