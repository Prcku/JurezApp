import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {RezervationService} from "../rezervation.service";
import {Rezervation} from "../rezervation";
import {UserService} from "../user.service";
import {User} from "../user";
import {Observable} from "rxjs";
import {NgbActiveModal, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {DatePipe, formatDate} from "@angular/common";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent{

  items: Rezervation[] | undefined;
  // private daysName = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  user$: Observable<User | undefined>;
  countUser: number | undefined;

  // Monday: Rezervation[] | undefined;
  // Wednesday: Rezervation[] | undefined;

  // today= new Date();
  // newToday = new Date();

  constructor(private rezervationService: RezervationService
    , private router: Router
    , private userService: UserService
    , public  _modalService: NgbModal) {

    // this.newToday.setDate( this.today.getDate() + 3 );
    this.user$ =this.userService.onUserChange()
    if (this.user$ != undefined) {
      this.reload();
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

  // const MODALS: {[name: string]: Type<any>} = {
  //   focusFirst: NgbdModalConfirm,
  //   autofocus: NgbdModalConfirmAutofocus
  // };
  //
  // open(name: string) { this._modalService.open(MODALS[name]); }

  rezerveTerm(rezervation: Rezervation){
    console.log("preco som tu < ? ")
    this.user$.subscribe(value => {
      if (value){
        if (confirm(`Naozaj sa chcete zaregistrovat na tento čas ${rezervation.currentTime} ?`)) {
          this.rezervationService.bookRezervation(rezervation.currentTime, value.id).subscribe(error =>{
            this.reload()
            // window.location.reload();
          });
        }
      }
    })
  }

}

// import { Type} from '@angular/core';
//
// @Component({
//   selector: 'ngbd-modal-confirm',
//   template: `
//   <div class="modal-header">
//     <h4 class="modal-title" id="modal-title">Profile deletion</h4>
//     <button type="button" class="btn-close" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')"></button>
//   </div>
//   <div class="modal-body">
//     <p><strong>Are you sure you want to delete <span class="text-primary">"John Doe"</span> profile?</strong></p>
//     <p>All information associated to this user profile will be permanently deleted.
//     <span class="text-danger">This operation can not be undone.</span>
//     </p>
//   </div>
//   <div class="modal-footer">
//     <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('cancel click')">Cancel</button>
//     <button type="button" class="btn btn-danger" (click)="modal.close('Ok click')">Ok</button>
//   </div>
//   `
// })
// export class NgbdModalConfirm {
//   constructor(public modal: NgbActiveModal) {}
// }
//
//
// const MODALS: {[name: string]: Type<any>} = {
//   focusFirst: NgbdModalConfirm,
// };
//
// @Component({selector: 'ngbd-modal-focus', templateUrl: './modal-focus.html'})
//
// export class NgbdModalFocus {
//   // withAutofocus = `<button type="button" ngbAutofocus class="btn btn-danger"
//   //     (click)="modal.close('Ok click')">Ok</button>`;
//
//   constructor(private _modalService: NgbModal) {}
//
//   open(name: string) { this._modalService.open(MODALS[name]); }
// }
