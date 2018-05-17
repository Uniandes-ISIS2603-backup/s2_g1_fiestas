(function (ng) {
    var mod = ng.module("loginModule");
    mod.constant("usuarioContext", "api/usuarios");
    mod.controller('loginCtrl', ['$scope', '$http', '$state', '$rootScope', 'usuarioContext',
        /**
         * @ngdoc controller
         * @name login.controller:loginCtrl
         * @description
         * Definición del controlador de Angular del módulo Login. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $rootScope Referencia injectada al Scope definido
         * para toda la aplicación.
         */
        function ($scope, $http, $state, $rootScope, usuarioContext) {

            $scope.user = {};
            $scope.data = {};

            $http.get(usuarioContext).then(function (response) {
                $scope.users = response.data;
            });

            /**
             * @ngdoc function
             * @name autenticar
             * @methodOf login.controller:loginCtrl
             * @description
             * Esta función procesa el inicio de sesión usando los datos del
             * $scope.
             */
            $scope.autenticar = function () {
                var flag = false;
                $http.post(usuarioContext, $scope.data).then(function (response) {

                    for (var item in $scope.users) {
                        if ($scope.users[item].login === response.data.login && $scope.users[item].contrasena === response.data.contrasena) {
                            flag = true;
                            $scope.user = $scope.users[item];
                            console.log($scope.user);
                            if ($scope.user.rol === "Cliente") {
                                $state.go('clienteDetail', {clienteId: $scope.user.token}, {reload: true});
                            } else if ($scope.user.rol === "Proveedor") {
                                $state.go('proveedorDetail', {proveedorId: $scope.user.token}, {reload: true});
                            } else {
                                $state.go('main', {}, {reload: true});
                            }
                            break;
                        }
                    }
                    if (!flag) {
                        $rootScope.alerts.push({type: "danger", msg: "Incorrect username or password."});
                    } else {
                        sessionStorage.token = $scope.user.token;
                        sessionStorage.setItem("username", $scope.user.login);
                        sessionStorage.setItem("name", $scope.user.nombre);
                        sessionStorage.setItem("rol", $scope.user.rol);
                        sessionStorage.setItem("id", $scope.user.id);
                        $rootScope.currentUser = $scope.user.nombre;
                        $rootScope.currentRol = $scope.user.rol;
                        $rootScope.currentToken = $scope.user.token;
                    }
                });
            };
        }
    ]);
}
)(window.angular);

