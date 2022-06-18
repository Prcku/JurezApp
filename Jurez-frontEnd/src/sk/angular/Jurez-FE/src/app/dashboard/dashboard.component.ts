import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {RezervationService} from "../rezervation.service";
import {Rezervation} from "../rezervation";
import {UserService} from "../user.service";
import {User} from "../user";
import {Observable} from "rxjs";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit{

  items: Rezervation[] | undefined;
  // private daysName = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  user$: Observable<User | undefined>;
  countUser: number | undefined;
  // Monday: Rezervation[] | undefined;
  // Wednesday: Rezervation[] | undefined;

  constructor(private rezervationService: RezervationService
    , private router: Router
    , private userService: UserService) {
    this.user$ =this.userService.onUserChange()
    if (this.user$ != undefined) {
      this.reload();
      // console.log(this.Wednesday)
      // console.log(this.items)
    }
  }

  reload(){
    this.rezervationService.getfreeRezervations().subscribe(
      value => {this.items = value
        // this.items.forEach(value1 => {
        //   if (this.daysName[new Date(value1.currentTime).getDay()] == 'Monday'){
        //     this.Monday?.push(value1);
        //   }
        //   if (this.daysName[new Date(value1.currentTime).getDay()] == 'Wednesday'){
        //     console.log(value1.currentTime)
        //     this.Wednesday?.push(value1);
        //   }
        // })
      },
    error => {console.log(error)})
    // this.rezervationService.getHowManyUserIsOnThisRezervation(this.item.currentTime).subscribe(value => {this.countUser = value});
    // console.log(this.item)
    // console.log(this.item.currentTime.getTime())
    // console.log("nech ti nejebe")
    // this.userService.get().subscribe((value: User) => {
    //   console.log("hodnota")
    //   console.log(value)
    //   this.user = value;
    // });
    // console.log(this.user)
  }

  rezerveTerm(rezervation: Rezervation){
    console.log("preco som tu < ? ")
    this.user$.subscribe(value => {
      if (value){
        if (confirm(`Naozaj sa chcete zaregistrovat na tento čas ${rezervation.currentTime} ?`)) {
          this.rezervationService.bookRezervation(rezervation.currentTime, value.id).subscribe(error =>{
            console.log(error)
            this.reload()
          });
        }
      }
    })
  }

  ngOnInit(): void {

  }
}
