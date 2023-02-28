import {Component, TemplateRef} from '@angular/core';
import {User} from "../user";
import {UserService} from "../user.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-all-rezervation',
  templateUrl: './all-rezervation.component.html',
  styleUrls: ['./all-rezervation.component.scss']
})
export class AllRezervationComponent {

    jojo: Map<string,User[]> | undefined;
    timeoptions: Date[] = [];
    modalRef!: BsModalRef;

    myForm: FormGroup;

  constructor(private userService: UserService,
              private modalService: BsModalService,
              private fb: FormBuilder) {
    this.myForm = this.fb.group({
      date: new Date(),
    });
    this.selected_date()
  }



  format = (input: number, padLength: number): string => {
    return input.toString().padStart(padLength, '0');
  };

  openModal(template: TemplateRef<any>) {
    this.modalRef = this.modalService.show(template);
  }

  selected_date() {
    this.myForm.value.date.setHours(6, 0, 0, 0);
    console.log(this.myForm.value.date)
    this.myForm.value.date =
      this.format(this.myForm.value.date.getFullYear(), 4) +
      '-' +
      this.format(this.myForm.value.date.getMonth() + 1, 2) +
      '-' +
      this.format(this.myForm.value.date.getDate(), 2) +
      ' ' +
      this.format(this.myForm.value.date.getHours(), 2) +
      ':' +
      this.format(this.myForm.value.date.getMinutes(), 2) +
      ':' +
      this.format(this.myForm.value.date.getSeconds(), 2);

    console.log(this.myForm.value.date)
    this.userService.getAllUsersInRezervationInDay(this.myForm.value.date).subscribe( value => {
      this.jojo = value;
      console.log(value)
    })
  }


}
