import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserService} from "./user.service";

@Injectable()
export class UserInterceptor implements HttpInterceptor {

  constructor(private user: UserService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (this.user.getToken()) {
      return next.handle(request.clone({
        setHeaders: {
          Authorization: 'Bearer ' + this.user.getToken()
        }
      }));
    }
    return next.handle(request)
  }
}
