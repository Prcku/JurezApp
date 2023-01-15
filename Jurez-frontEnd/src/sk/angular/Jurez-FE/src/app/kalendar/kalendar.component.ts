import { Component,OnInit } from '@angular/core';
import {RezervationService} from "../rezervation.service";
import {Router} from "@angular/router";
import {Observable} from "rxjs";
import {User} from "../user";
import {UserService} from "../user.service";
import { DatePipe } from '@angular/common'
import {Rezervation} from "../rezervation";

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

  constructor(private rezervationService: RezervationService
    , private router: Router
    , private userService: UserService
    , public datepipe: DatePipe) {

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

  availibleRezervation(time: Date){
    if (time != undefined){
      let time_In_format =this.datepipe.transform(time, 'yyyy-MM-dd HH:mm');
      this.rezervationService.getRezervationOnThisTime(time_In_format).subscribe(
        value => {this.countRezervation = value
          console.log(value)
      })
    }
  }

  rezerveTerm(time: Date){
    this.user$.subscribe(value => {
      if (value){
        let time_In_format =this.datepipe.transform(time, 'yyyy-MM-dd HH:mm');
        if (confirm(`Naozaj sa chcete zaregistrovat na tento čas ${time_In_format} ?`)) {
          this.rezervationService.bookRezervation(time_In_format, value.id).subscribe(error =>{
            this.reload()
            window.location.reload();
          });
        }
      }
    })
  }
}
