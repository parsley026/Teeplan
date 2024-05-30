import {initializeApp} from "firebase/app";
import {getAuth, signInWithEmailAndPassword} from "firebase/auth";
import {getDatabase, ref, get, child, set, push, remove} from "firebase/database";

const firebaseConfig = {
    apiKey: "AIzaSyAh8ZO7qR_Mkrgqg2f4hlEQIz1tZmHbON4",
    authDomain: "fir-tutorial-4-1dccd.firebaseapp.com",
    databaseURL: "https://fir-tutorial-4-1dccd-default-rtdb.europe-west1.firebasedatabase.app",
    projectId: "fir-tutorial-4-1dccd",
    storageBucket: "fir-tutorial-4-1dccd.appspot.com",
    messagingSenderId: "1038074967261",
    appId: "1:1038074967261:web:c2e107f162ae373b375734"
};


// Initialize Firebase
const app = initializeApp(firebaseConfig);

// Initialize Other Features
const auth = getAuth(app);
const database = getDatabase(app);

// Initialize registerUser
// const register = document.getElementById('register');
// register.addEventListener("click", function(event) {
//   event.preventDefault();

//   const first_name = "none";
//   const last_name = "none";
//   const is_admin = false;
//   const email = document.getElementById("email_input").value;
//   const password = document.getElementById("password_input").value;

//   const data = {
//     first_name : first_name,
//     last_name : last_name,
//     is_admin : is_admin,
//     email : email,
//     password : password
//   };

//   createUserWithEmailAndPassword(auth, email, password)
//   .then((credentials) => {
//     const ID = auth.currentUser.uid;
//     alert();
//     set(ref(database, 'users/' + ID), data)
//   })
//   .catch((error) => {
//     const errorCode = error.code;
//     const errorMessage = error.message;
//   });
// })

// Initialize loginAdmin
export function login(email, password, callback) {
    signInWithEmailAndPassword(auth, email, password)
        .then((credentials) => {
            get(child(ref(database), 'users/' + credentials.user.uid)).then((snapshot) => {
                const data = snapshot.val();
                if (snapshot.exists() && data.is_admin === true) {
                    callback(true, null);
                } else {
                    callback(false, "user is not an admin");
                }
            });
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;

            callback(false, "Incorrect login or password");
            console.error(errorCode + errorMessage);
        });
}

// Initialize getUsers
export async function getUsers(search) {
    try {
        const snapshot = await get(child(ref(database), 'users'));
        const users = [];
        if (snapshot.exists()) {
            snapshot.forEach((childSnapshot) => {
                const user = childSnapshot.val();
                user.id = childSnapshot.key;
                users.push(user);
            });
        }
        return users;
    } catch (error) {
        const errorCode = error.code;
        const errorMessage = error.message;

        console.error("getUsers failed")
        console.error(errorCode + errorMessage);
        return [];
    }
}

// Initialize getCoupons
export async function getCoupons(search) {
    try {
        const snapshot = await get(child(ref(database), 'coupons'));
        const coupons = [];
        if (snapshot.exists()) {
            snapshot.forEach((childSnapshot) => {
                const coupon = childSnapshot.val();
                coupon.id = childSnapshot.key;
                coupons.push(coupon);
            });
        }
        return coupons;
    } catch (error) {
        const errorCode = error.code;
        const errorMessage = error.message;

        console.error("getUsers failed")
        console.error(errorCode + errorMessage);
        return [];
    }
}

// Initialize getEvents
export async function getEvents(search) {
    try {
        const snapshot = await get(child(ref(database), 'events'));
        const events = [];
        if (snapshot.exists()) {
            snapshot.forEach((childSnapshot) => {
                const event = childSnapshot.val();
                event.id = childSnapshot.key;
                events.push(event);
            });
        }
        return events;
    } catch (error) {
        const errorCode = error.code;
        const errorMessage = error.message;

        console.error("getEvents failed")
        console.error(errorCode + errorMessage);
        return [];
    }
}

// Initialize addCoupon
export function addCoupon(name, description, code) {
    try {
        const data = {
            name: name,
            description: description,
            code: code,
        };
    
        const ID = push(ref(database, 'coupons/'));
        set(ID, data);
    } catch (error) {
        const errorCode = error.code;
        const errorMessage = error.message;

        console.error("addCoupon failed")
        console.error(errorCode + errorMessage);
    }
}

