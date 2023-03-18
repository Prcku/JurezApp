import {Component, OnInit, TemplateRef} from '@angular/core';
import {RezervationService} from "../rezervation.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../user";
import {UserService} from "../user.service";
import { DatePipe } from '@angular/common'
import {Rezervation} from "../rezervation";
import {FormBuilder, FormGroup} from "@angular/forms";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-kalendar',
  templateUrl: './kalendar.component.html',
  styleUrls: ['./kalendar.component.scss']
})
export class KalendarComponent  {

  items!: Rezervation[][];
  item!: Rezervation[];
  user$: Observable<User | undefined>;
  countRezervation: number | undefined;
  cancel_time!: Date;
  myForm: FormGroup;
  modalRef!: BsModalRef;

  constructor(private rezervationService: RezervationService
    , private router: Router
    , private userService: UserService
    , private modalService: BsModalService
    , private fb: FormBuilder
    , public datepipe: DatePipe) {

    this.myForm = this.fb.group({
      date: new Date(),
    });

    this.user$ =this.userService.onUserChange()
    if (this.user$ != undefined) {
      this.reload();
    }

  }

  reload(){
    this.rezervationService.getGeneratedRezervation().subscribe(
      value => {this.items = value
        console.log(value)
      },
      error => {console.log(error)})
  }

  openModal(template: TemplateRef<any>, cancel_Time: Date) {
    this.cancel_time = cancel_Time;
    this.modalRef = this.modalService.show(template, {class: 'modal-sm'});
  }

  confirm(): void {
    this.user$.subscribe(value => {
      if (value){
        let time_In_format =this.datepipe.transform(this.cancel_time, 'yyyy-MM-dd HH:mm');
        this.rezervationService.bookRezervation(time_In_format, value.id).subscribe(error =>{
          this.reload()
        });
      }
    })
    this.modalRef.hide();
  }

  decline(): void {
    this.modalRef.hide();
  }

}
