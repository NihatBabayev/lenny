var adminDB = db.getSiblingDB('admin');

// Change the following line with a strong and unique password
adminDB.auth('admin', 'password');

if (adminDB.getMongo().getDBNames().indexOf('lenny') === -1) {
    // adminDB.runCommand({ create: 'lenny' });
    //
    // var lennyDB = db.getSiblingDB('lenny');
    //
    // lennyDB.createUser({
    //     user: 'niko',
    //     pwd: 'niko1234',
    //     roles: ['readWrite', 'dbAdmin']
    // });
    //
    // lennyDB.auth('admin', 'password');
    //
    // lennyDB.createCollection('products');

    print('Initialization script completed successfully');
} else {
    print('Database "lenny" already exists. Initialization skipped.');
}

quit();
