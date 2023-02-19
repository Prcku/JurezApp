import {Component, TemplateRef} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {Router} from "@angular/router";
import {NgForm} from "@angular/forms";
// import { BsModalService, BsModalRef } from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
})
export class NewUserComponent  {

  // modalRef: BsModalRef | undefined;
  item = {} as User;

  constructor(private users: UserService,
              private router: Router,
              //private modalService: BsModalService
                ) {

  }

  // openModal(template: TemplateRef<any>) {
  //   this.modalRef = this.modalService.show(template);
  // }

  submit(formElemnt: NgForm){
    if (formElemnt.invalid){
      return
    }


    this.users.add(this.item).subscribe(
      () => (console.log("ahoj")),
      error => {
        if (error.message == "Duplicity"){
        console.log("vypis na obrazovku zadal si rovanky mail zmen ho")
      }},
      () => (this.router.navigate(['/login']))
    )
  }
}
