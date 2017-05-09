(function() {
    'use strict';
    angular
        .module('cavmgrApp')
        .factory('ClientPasswords', ClientPasswords);

    ClientPasswords.$inject = ['$resource', 'DateUtils'];

    function ClientPasswords ($resource, DateUtils) {
        var resourceUrl =  'api/client-passwords/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                        data.lastUpdateDate = DateUtils.convertDateTimeFromServer(data.lastUpdateDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
