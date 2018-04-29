/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function (ng) {
    var mod = ng.module("blogsModule");
    mod.constant("bonosContext", "api/bonos");
    mod.controller('bonosUpdateCtrl', ['$scope', '$http', 'bonosContext', '$state','$rootScope',
        /**
         * @ngdoc controller
         * @name eventos.controller:bonosUpdateCtrl
         * @description
         * Definición del controlador auxiliar para actualizar Bonos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} bonosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} $filter Dependencia injectada para hacer filtros sobre
         * arreglos.
         */
        function ($scope, $http, bonosContext, $state,$rootScope) {
            
            $rootScope.edit = true;

            $scope.data = {};

            $scope.selectedItems = [];

            $scope.availableItems = [];

            var idBono = $state.params.bonosId;

            //Consulto el bono a editar.
            $http.get(bonosContext + '/' + idBono).then(function (response) {
                var bono = response.data;
                $scope.data.aplicaDesde = new Date(bono.aplicaDesde);
                $scope.data.codigo = bono.codigo;
                $scope.data.descuento = bono.descuento;
                $scope.data.expira = new Date(bono.expira);
                $scope.data.motivo = bono.moyivo;
                $scope.data.proveedor = bono.proveedor;
            });

            /**
             * @ngdoc function
             * @name updateEvento
             * @methodOf eventos.controller:eventoUpdateCtrl
             * @description
             * Actualiza un evento con la información del
             * $scope.
             */
            $scope.updateBonos = function () {
                $http.put(bonosContext + "/" + idBono, $scope.data).then(function (response) {
                    //Bono created successfully
                    $state.go('bonosList', {bonosId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);

